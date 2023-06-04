package com.soccerchallenge.data.datasource

import com.soccerchallenge.data.database.dao.MatchDao
import com.soccerchallenge.data.database.entity.mapper.MatchEntityMapper
import com.soccerchallenge.data.network.model.mapper.toMatch
import com.soccerchallenge.data.utils.ModelCreator
import com.soccerchallenge.domain.model.MatchType
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LocalSoccerDataSourceImplTest {

    private lateinit var dataSource: LocalSoccerDataSource

    @MockK
    private lateinit var matchDao: MatchDao

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = LocalSoccerDataSourceImpl(matchDao)
    }

    @Test
    fun `should receive fixture when fetch fixture`() = runTest {
        val fixture = ModelCreator.fixture.map { it.toMatch() }
        val fixtureEntities = fixture.map { MatchEntityMapper.asEntity(it) }
        coEvery { matchDao.getMatches(MatchType.FIXTURE) } returns fixtureEntities
        val entities = dataSource.fetchFixtureFromLocal()
        assertEquals(fixtureEntities, entities)
    }

    @Test
    fun `should receive results when fetch results`() = runTest {
        val results = ModelCreator.results.map { it.toMatch() }
        val resultsEntities = results.map { MatchEntityMapper.asEntity(it) }
        coEvery { matchDao.getMatches(MatchType.RESULT) } returns resultsEntities
        val entities = dataSource.fetchMatchResultsFromLocal()
        assertEquals(resultsEntities, entities)
    }

    @Test
    fun `should execute insert when inserting matches`() = runTest {
        val results = ModelCreator.results.map { it.toMatch() }
        val resultsEntities = results.map { MatchEntityMapper.asEntity(it) }
        coEvery { matchDao.insertMatches(resultsEntities) } returns Unit
        dataSource.insertMatches(resultsEntities)
        coVerify { matchDao.insertMatches(resultsEntities) }
    }

    @Test
    fun `should execute delete when deleting matches`() = runTest {
        coEvery { matchDao.deleteMatches(MatchType.RESULT) } returns Unit
        dataSource.deleteMatches(MatchType.RESULT)
        coVerify { matchDao.deleteMatches(MatchType.RESULT) }
    }

    @Test
    fun `should receive match when getting a match`() = runTest {
        val results = ModelCreator.results.map { it.toMatch() }
        val resultEntity = MatchEntityMapper.asEntity(results.first())
        coEvery { matchDao.getMatch(resultEntity.id) } returns resultEntity
        val match = dataSource.fetchMatchFromLocal(resultEntity.id)
        assertEquals(match, resultEntity)
    }

}