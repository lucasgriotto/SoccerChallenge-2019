package com.soccerchallenge.data.datasource

import com.soccerchallenge.data.network.service.SoccerService
import com.soccerchallenge.data.utils.ModelCreator
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RemoteSoccerDataSourceImplTest {

    private lateinit var dataSource: RemoteSoccerDataSource

    @MockK
    private lateinit var soccerService: SoccerService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = RemoteSoccerDataSourceImpl(soccerService)
    }

    @Test
    fun `should receive fixture when fetch fixture success`() = runTest {
        val fixtureResponse = ModelCreator.fixture
        coEvery { soccerService.fetchFixture() } returns fixtureResponse
        val response = dataSource.fetchFixture()
        assertEquals(fixtureResponse, response)
    }

    @Test
    fun `should receive results when fetch results success`() = runTest {
        val resultsResponse = ModelCreator.results
        coEvery { soccerService.fetchResults() } returns resultsResponse
        val response = dataSource.fetchMatchResults()
        assertEquals(resultsResponse, response)
    }

}