package com.lucas.soccerchallenge.repository

import app.cash.turbine.test
import com.lucas.soccerchallenge.api.SoccerService
import com.lucas.soccerchallenge.data.entity.toMatch
import com.lucas.soccerchallenge.utils.ModelCreator
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection

private const val EMPTY_STRING = ""

@ExperimentalCoroutinesApi
class SoccerRepositoryImplTest {

    private lateinit var repository: SoccerRepositoryImpl

    @MockK
    private lateinit var soccerService: SoccerService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = SoccerRepositoryImpl(soccerService, UnconfinedTestDispatcher())
    }

    @Test
    fun `should await fixture when fetch fixture success`() = runTest {
        val fixtureResponse = ModelCreator.fixture
        coEvery { soccerService.getFixture() } returns Response.success(fixtureResponse)
        val fixture = fixtureResponse.map { it.toMatch() }
        repository.fetchFixture().test {
            val data = awaitItem()
            assertEquals(fixture, data)
            awaitComplete()
        }
    }

    @Test
    fun `should await error when fetch fixture fail`() = runTest {
        coEvery { soccerService.getFixture() } returns Response.error(
            HttpURLConnection.HTTP_UNAUTHORIZED, EMPTY_STRING.toResponseBody()
        )
        repository.fetchFixture().test {
            val error = awaitError()
            assertThat(error, instanceOf(HttpException::class.java))
        }
    }

    @Test
    fun `should await results when fetch results success`() = runTest {
        val resultResponse = ModelCreator.results
        coEvery { soccerService.getResults() } returns Response.success(resultResponse)
        val results = resultResponse.map { it.toMatch() }
        repository.fetchMatchResults().test {
            val data = awaitItem()
            assertEquals(results, data)
            awaitComplete()
        }
    }

    @Test
    fun `should await error when fetch results fail`() = runTest {
        coEvery { soccerService.getResults() } returns Response.error(
            HttpURLConnection.HTTP_UNAUTHORIZED, EMPTY_STRING.toResponseBody()
        )
        repository.fetchMatchResults().test {
            val error = awaitError()
            assertThat(error, instanceOf(HttpException::class.java))
        }
    }

}
