package com.lucas.soccerchallenge.repository

import com.lucas.soccerchallenge.api.SoccerService
import com.lucas.soccerchallenge.utils.ModelCreator
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection

class SoccerRepositoryImplTest {

    private lateinit var repository: SoccerRepositoryImpl

    @MockK
    private lateinit var soccerService: SoccerService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = SoccerRepositoryImpl(soccerService)
    }

    @Test
    fun `test FetchFixture success`() = runBlocking {
        val fixtureResponse = ModelCreator.fixture
        coEvery { soccerService.getFixture() } returns Response.success(fixtureResponse)
        val fixture = repository.fetchFixture()
        assertEquals(fixtureResponse.size, fixture.size)
    }

    @Test
    fun `test FetchFixture error`() {
        runBlocking {
            coEvery { soccerService.getFixture() } returns Response.error(
                HttpURLConnection.HTTP_UNAUTHORIZED,
                "".toResponseBody()
            )
            try {
                repository.fetchFixture()
            } catch (e: Exception) {
                assertTrue(e is HttpException)
            }
        }
    }

    @Test
    fun `test FetchMatchResults success`() = runBlocking {
        val resultsResponse = ModelCreator.results
        coEvery { soccerService.getResults() } returns Response.success(resultsResponse)
        val results = repository.fetchMatchResults()
        assertEquals(resultsResponse.size, results.size)
    }

    @Test
    fun `test FetchMatchResults error`() {
        runBlocking {
            coEvery { soccerService.getResults() } returns Response.error(
                HttpURLConnection.HTTP_UNAUTHORIZED,
                "".toResponseBody()
            )
            try {
                repository.fetchMatchResults()
            } catch (e: Exception) {
                assertTrue(e is HttpException)
            }
        }
    }

}