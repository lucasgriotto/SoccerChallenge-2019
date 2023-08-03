package com.soccerchallenge.data.repository

import androidx.annotation.WorkerThread
import com.soccerchallenge.data.database.entity.mapper.MatchEntityMapper
import com.soccerchallenge.data.datasource.LocalSoccerDataSource
import com.soccerchallenge.data.datasource.RemoteSoccerDataSource
import com.soccerchallenge.data.di.qualifier.IODispatcher
import com.soccerchallenge.data.network.model.mapper.toMatch
import com.soccerchallenge.data.repository.util.safeRepositoryCall
import com.soccerchallenge.domain.model.Match
import com.soccerchallenge.domain.model.MatchType
import com.soccerchallenge.domain.repository.SoccerRepository
import com.soccerchallenge.domain.util.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SoccerRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteSoccerDataSource,
    private val localDataSource: LocalSoccerDataSource,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : SoccerRepository {

    @WorkerThread
    override suspend fun fetchFixture(forceRefresh: Boolean) = safeRepositoryCall {
        if (forceRefresh) fetchFixtureRemotely() else fetchFixtureLocally()
    }

    @WorkerThread
    private suspend fun fetchFixtureRemotely(): List<Match> {
        val matches = remoteDataSource.fetchFixture()
        return withContext(dispatcher) {
            matches.map { it.toMatch() }
                .sortedBy { it.date }
                .also { fixture ->
                    localDataSource.deleteMatches(MatchType.FIXTURE)
                    insertMatches(fixture)
                }
        }
    }

    @WorkerThread
    private suspend fun fetchFixtureLocally(): List<Match> {
        val matches = localDataSource.fetchFixtureFromLocal()
        return withContext(dispatcher) {
            matches.map { MatchEntityMapper.asDomain(it) }
                .ifEmpty { fetchFixtureRemotely() }
        }
    }

    override suspend fun fetchMatchResults(forceRefresh: Boolean) = safeRepositoryCall {
        if (forceRefresh) fetchMatchResultsRemotely() else fetchMatchResultsLocally()
    }

    @WorkerThread
    private suspend fun fetchMatchResultsRemotely(): List<Match> {
        val matches = remoteDataSource.fetchMatchResults()
        return withContext(dispatcher) {
            matches.map { it.toMatch() }
                .sortedBy { it.date }
                .also { results ->
                    localDataSource.deleteMatches(MatchType.RESULT)
                    insertMatches(results)
                }
        }
    }

    @WorkerThread
    private suspend fun fetchMatchResultsLocally(): List<Match> {
        val matches = localDataSource.fetchMatchResultsFromLocal()
        return withContext(dispatcher) {
            matches.map { MatchEntityMapper.asDomain(it) }
                .ifEmpty { fetchMatchResultsRemotely() }
        }
    }

    @WorkerThread
    override suspend fun fetchMatch(matchId: Int): Response<Match> = safeRepositoryCall {
        val matchEntity = localDataSource.fetchMatchFromLocal(matchId)
        MatchEntityMapper.asDomain(matchEntity)
    }

    @WorkerThread
    private suspend fun insertMatches(matches: List<Match>) {
        localDataSource.insertMatches(matches.map { MatchEntityMapper.asEntity(it) })
    }

}