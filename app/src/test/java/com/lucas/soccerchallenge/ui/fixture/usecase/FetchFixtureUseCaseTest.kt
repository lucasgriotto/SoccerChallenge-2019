package com.lucas.soccerchallenge.ui.fixture.usecase

import app.cash.turbine.test
import com.lucas.soccerchallenge.core.data.repository.SoccerRepository
import com.lucas.soccerchallenge.core.network.model.mapper.toMatch
import com.lucas.soccerchallenge.ui.base.Resource
import com.lucas.soccerchallenge.utils.ModelCreator
import com.lucas.soccerchallenge.utils.errorfactory.AppError
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.supervisorScope
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

    private val fixture = ModelCreator.fixture.map { it.toMatch() }

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        useCase = FetchFixtureUseCase(repository)
    }

    @Test
    fun `should await fixture when fetch fixture success`() = runTest {
        coEvery { repository.fetchFixture(true) } returns fixture
        useCase.result.test {
            useCase.execute(this@runTest, FetchFixtureUseCase.Params(forceRefresh = true))
            val initialize = awaitItem()
            assertThat(initialize, instanceOf(Resource.Initialize::class.java))
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
        coEvery { repository.fetchFixture(true) } throws ConnectException()
        useCase.result.test {
            supervisorScope {
                useCase.execute(this, FetchFixtureUseCase.Params(forceRefresh = true))
            }
            val initialize = awaitItem()
            assertThat(initialize, instanceOf(Resource.Initialize::class.java))
            val loading = awaitItem()
            assertThat(loading, instanceOf(Resource.Loading::class.java))
            val error = awaitItem()
            assertThat(error, instanceOf(Resource.Error::class.java))
            val appError = (error as Resource.Error).error
            assertThat(appError, instanceOf(AppError.Connection::class.java))
        }
    }

}
