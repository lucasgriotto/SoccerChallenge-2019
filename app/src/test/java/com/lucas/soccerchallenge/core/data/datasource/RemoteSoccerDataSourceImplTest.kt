package com.lucas.soccerchallenge.core.data.datasource

import com.lucas.soccerchallenge.core.network.exceptions.NullBodyException
import com.lucas.soccerchallenge.core.network.model.MatchResponse
import com.lucas.soccerchallenge.core.network.service.SoccerService
import com.lucas.soccerchallenge.utils.ModelCreator
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection

private const val EMPTY_STRING = ""

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
        coEvery { soccerService.getFixture() } returns Response.success(fixtureResponse)
        val response = dataSource.fetchFixture()
        assertEquals(fixtureResponse, response)
    }

    @Test(expected = HttpException::class)
    fun `should receive error when fetch fixture fail`() = runTest {
        val error = Response.error<List<MatchResponse>>(
            HttpURLConnection.HTTP_UNAUTHORIZED, EMPTY_STRING.toResponseBody()
        )
        coEvery { soccerService.getFixture() } returns error
        dataSource.fetchFixture()
    }

    @Test
    fun `should receive results when fetch results success`() = runTest {
        val resultsResponse = ModelCreator.results
        coEvery { soccerService.getResults() } returns Response.success(resultsResponse)
        val response = dataSource.fetchMatchResults()
        assertEquals(resultsResponse, response)
    }

    @Test(expected = HttpException::class)
    fun `should receive error when fetch results fail`() = runTest {
        val error = Response.error<List<MatchResponse>>(
            HttpURLConnection.HTTP_UNAUTHORIZED, EMPTY_STRING.toResponseBody()
        )
        coEvery { soccerService.getResults() } returns error
        dataSource.fetchMatchResults()
    }

    @Test(expected = NullBodyException::class)
    fun `should receive error when response body is null`() = runTest {
        coEvery { soccerService.getFixture() } returns Response.success(null)
        dataSource.fetchFixture()
    }

}
