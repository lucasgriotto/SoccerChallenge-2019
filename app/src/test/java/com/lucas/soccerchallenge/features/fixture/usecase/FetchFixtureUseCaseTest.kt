package com.lucas.soccerchallenge.features.fixture.usecase

import app.cash.turbine.test
import com.lucas.soccerchallenge.core.networking.AppError
import com.lucas.soccerchallenge.core.networking.Resource
import com.lucas.soccerchallenge.data.entity.toMatch
import com.lucas.soccerchallenge.repository.SoccerRepository
import com.lucas.soccerchallenge.utils.ModelCreator
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.ConnectException

@ExperimentalCoroutinesApi
class FetchFixtureUseCaseTest {

    private lateinit var useCase: FetchFixtureUseCase

    @MockK
    private lateinit var repository: SoccerRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        useCase = FetchFixtureUseCase(repository)
    }

    @Test
    fun `should await fixture when fetch fixture success`() = runTest {
        val fixture = ModelCreator.fixture.map { it.toMatch() }
        coEvery { repository.fetchFixture() } returns flowOf(fixture)
        useCase.result.test {
            val initialize = awaitItem()
            assertThat(initialize, instanceOf(Resource.Initialize::class.java))
        }
        useCase.execute(this, Unit)
        useCase.result.test {
            val loading = awaitItem()
            assertThat(loading, instanceOf(Resource.Loading::class.java))
            val success = awaitItem()
            assertThat(success, instanceOf(Resource.Success::class.java))
            val data = (success as Resource.Success).data
            assertEquals(fixture, data)
        }
    }

    @Test
    fun `should await error when fetch fixture fail`() = runTest {
        coEvery { repository.fetchFixture() } returns flow { throw ConnectException() }
        useCase.execute(this, Unit)
        useCase.result.test {
            val loading = awaitItem()
            assertThat(loading, instanceOf(Resource.Loading::class.java))
            val error = awaitItem()
            assertThat(error, instanceOf(Resource.Error::class.java))
            val appError = (error as Resource.Error).error
            assertThat(appError, instanceOf(AppError.Connection::class.java))
        }
    }

}