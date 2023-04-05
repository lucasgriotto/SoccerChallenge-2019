package com.lucas.soccerchallenge.core.network.utils

import com.lucas.soccerchallenge.core.network.exceptions.NullBodyException
import com.lucas.soccerchallenge.core.network.service.SoccerService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection

private const val EMPTY_STRING = ""

@ExperimentalCoroutinesApi
class SafeApiCallTest {

    @MockK
    private lateinit var soccerService: SoccerService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test(expected = NullBodyException::class)
    fun `should throw null body exception when body is null`() = runTest {
        coEvery { soccerService.getResults() } returns Response.success(null)
        safeApiCall { soccerService.getResults() }
        Unit
    }

    @Test(expected = HttpException::class)
    fun `should throw http exception when call fails`() = runTest {
        coEvery { soccerService.getResults() } returns
                Response.error(HttpURLConnection.HTTP_UNAUTHORIZED, EMPTY_STRING.toResponseBody())
        safeApiCall { soccerService.getResults() }
        Unit
    }

}