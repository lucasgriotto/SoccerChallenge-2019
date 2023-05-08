package com.lucas.soccerchallenge.core.data.repository

import androidx.annotation.WorkerThread
import com.lucas.soccerchallenge.core.data.datasource.LocalSoccerDataSource
import com.lucas.soccerchallenge.core.data.datasource.RemoteSoccerDataSource
import com.lucas.soccerchallenge.core.database.entity.mapper.MatchEntityMapper
import com.lucas.soccerchallenge.core.model.Match
import com.lucas.soccerchallenge.core.model.MatchType
import com.lucas.soccerchallenge.core.network.model.mapper.toMatch
import com.lucas.soccerchallenge.di.qualifier.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SoccerRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteSoccerDataSource,
    private val localDataSource: LocalSoccerDataSource,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : SoccerRepository {

    override suspend fun fetchMatch(matchId: Int): Match {
        val matchEntity = localDataSource.fetchMatchFromLocal(matchId)
        return MatchEntityMapper.asDomain(matchEntity)
    }

    @WorkerThread
    override suspend fun fetchFixture(forceRefresh: Boolean): List<Match> =
        if (forceRefresh) fetchFixtureRemotely() else fetchFixtureLocally()

    @WorkerThread
    override suspend fun fetchMatchResults(forceRefresh: Boolean): List<Match> =
        if (forceRefresh) fetchMatchResultsRemotely() else fetchMatchResultsLocally()

    @WorkerThread
    private suspend fun fetchFixtureLocally(): List<Match> {
        val matches = localDataSource.fetchFixtureFromLocal()
        return withContext(dispatcher) {
            matches.map { MatchEntityMapper.asDomain(it) }
                .ifEmpty { fetchFixtureRemotely() }
        }
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
    private suspend fun fetchMatchResultsLocally(): List<Match> {
        val matches = localDataSource.fetchMatchResultsFromLocal()
        return withContext(dispatcher) {
            matches.map { MatchEntityMapper.asDomain(it) }
                .ifEmpty { fetchMatchResultsRemotely() }
        }
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
    private suspend fun insertMatches(matches: List<Match>) {
        localDataSource.insertMatches(matches.map { MatchEntityMapper.asEntity(it) })
    }

}
