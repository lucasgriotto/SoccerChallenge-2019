package com.soccerchallenge.data.repository

import com.soccerchallenge.data.database.entity.mapper.MatchEntityMapper
import com.soccerchallenge.data.datasource.LocalSoccerDataSource
import com.soccerchallenge.data.datasource.RemoteSoccerDataSource
import com.soccerchallenge.data.network.model.mapper.toMatch
import com.soccerchallenge.data.utils.ModelCreator
import com.soccerchallenge.domain.model.Match
import com.soccerchallenge.domain.model.MatchType
import com.soccerchallenge.domain.util.Response
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.ConnectException

@ExperimentalCoroutinesApi
class SoccerRepositoryImplTest {

    private lateinit var repository: SoccerRepositoryImpl

    @MockK
    private lateinit var remoteDataSource: RemoteSoccerDataSource

    @MockK
    private lateinit var localDataSource: LocalSoccerDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = SoccerRepositoryImpl(
            remoteDataSource, localDataSource, UnconfinedTestDispatcher()
        )
    }

    @Test
    fun `should receive fixture from network when refresh true and fetch fixture success`() =
        runTest {
            val fixtureResponse = ModelCreator.fixture
            val fixture = fixtureResponse.map { it.toMatch() }
            val fixtureEntities = fixture.map { MatchEntityMapper.asEntity(it) }

            coEvery { remoteDataSource.fetchFixture() } returns fixtureResponse
            coEvery { localDataSource.deleteMatches(MatchType.FIXTURE) } returns Unit
            coEvery { localDataSource.insertMatches(fixtureEntities) } returns Unit

            val matches = repository.fetchFixture(true)

            coVerify { localDataSource.deleteMatches(MatchType.FIXTURE) }
            coVerify { localDataSource.insertMatches(fixtureEntities) }

            assertEquals(Response.Success(fixture), matches)
        }

    @Test
    fun `should receive fixture from network when refresh false and local data is empty`() =
        runTest {
            val fixtureResponse = ModelCreator.fixture
            val fixture = fixtureResponse.map { it.toMatch() }
            val fixtureEntities = fixture.map { MatchEntityMapper.asEntity(it) }

            coEvery { localDataSource.fetchFixtureFromLocal() } returns emptyList()
            coEvery { remoteDataSource.fetchFixture() } returns fixtureResponse
            coEvery { localDataSource.deleteMatches(MatchType.FIXTURE) } returns Unit
            coEvery { localDataSource.insertMatches(fixtureEntities) } returns Unit

            val matches = repository.fetchFixture(false)

            coVerify { localDataSource.fetchFixtureFromLocal() }
            coVerify { localDataSource.deleteMatches(MatchType.FIXTURE) }
            coVerify { localDataSource.insertMatches(fixtureEntities) }

            assertEquals(Response.Success(fixture), matches)
        }

    @Test
    fun `should receive fixture from database when refresh false and local data is not empty`() =
        runTest {
            val fixtureResponse = ModelCreator.fixture
            val fixture = fixtureResponse.map { it.toMatch() }
            val fixtureEntities = fixture.map { MatchEntityMapper.asEntity(it) }

            coEvery { localDataSource.fetchFixtureFromLocal() } returns fixtureEntities

            val matches = repository.fetchFixture(false)

            assertEquals(Response.Success(fixture), matches)
        }

    @Test
    fun `should receive results from network when refresh true and fetch results success`() =
        runTest {
            val resultsResponse = ModelCreator.results
            val results = resultsResponse.map { it.toMatch() }.sortedBy { it.date }
            val resultsEntities = results.map { MatchEntityMapper.asEntity(it) }

            coEvery { remoteDataSource.fetchMatchResults() } returns resultsResponse
            coEvery { localDataSource.deleteMatches(MatchType.RESULT) } returns Unit
            coEvery { localDataSource.insertMatches(resultsEntities) } returns Unit

            val matches = repository.fetchMatchResults(true)

            coVerify { localDataSource.deleteMatches(MatchType.RESULT) }
            coVerify { localDataSource.insertMatches(resultsEntities) }

            assertEquals(Response.Success(results), matches)
        }

    @Test
    fun `should receive results from network when refresh false and local data is empty`() =
        runTest {
            val resultsResponse = ModelCreator.results
            val results = resultsResponse.map { it.toMatch() }.sortedBy { it.date }
            val resultsEntities = results.map { MatchEntityMapper.asEntity(it) }

            coEvery { localDataSource.fetchMatchResultsFromLocal() } returns emptyList()
            coEvery { remoteDataSource.fetchMatchResults() } returns resultsResponse
            coEvery { localDataSource.deleteMatches(MatchType.RESULT) } returns Unit
            coEvery { localDataSource.insertMatches(resultsEntities) } returns Unit

            val matches = repository.fetchMatchResults(false)

            coVerify { localDataSource.fetchMatchResultsFromLocal() }
            coVerify { localDataSource.deleteMatches(MatchType.RESULT) }
            coVerify { localDataSource.insertMatches(resultsEntities) }

            assertEquals(Response.Success(results), matches)
        }

    @Test
    fun `should receive results from database when refresh false and local data is not empty`() =
        runTest {
            val resultsResponse = ModelCreator.results
            val results = resultsResponse.map { it.toMatch() }
            val resultsEntities = results.map { MatchEntityMapper.asEntity(it) }

            coEvery { localDataSource.fetchMatchResultsFromLocal() } returns resultsEntities

            val matches = repository.fetchMatchResults(false)

            assertEquals(Response.Success(results), matches)
        }

    @Test
    fun `should receive match from database when fetching match`() = runTest {
        val resultMatch = ModelCreator.results.first().toMatch()
        val resultEntity = MatchEntityMapper.asEntity(resultMatch)
        coEvery { localDataSource.fetchMatchFromLocal(resultEntity.id) } returns resultEntity
        val match = repository.fetchMatch(resultEntity.id)
        assertEquals(Response.Success(resultMatch), match)
    }

    @Test
    fun `should receive error when fetch fixture fail`() = runTest {
        val error = ConnectException()
        coEvery { remoteDataSource.fetchFixture() } throws error
        val response = repository.fetchFixture(true)
        assertEquals(Response.Error<List<Match>>(error), response)
    }

    @Test
    fun `should receive error when fetch result fail`() = runTest {
        val error = ConnectException()
        coEvery { remoteDataSource.fetchMatchResults() } throws error
        val response = repository.fetchMatchResults(true)
        assertEquals(Response.Error<List<Match>>(error), response)
    }

}