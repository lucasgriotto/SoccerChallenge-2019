package com.lucas.soccerchallenge.ui.fixture

import app.cash.turbine.test
import com.lucas.soccerchallenge.ui.fixture.adapter.toFixtureDisplayModel
import com.lucas.soccerchallenge.ui.home.match.MatchScreenState
import com.lucas.soccerchallenge.utils.MainDispatcherRule
import com.lucas.soccerchallenge.utils.ModelCreator
import com.lucas.soccerchallenge.utils.errorfactory.AppError
import com.soccerchallenge.data.network.model.mapper.toMatch
import com.soccerchallenge.domain.usecase.FetchFixtureUseCase
import com.soccerchallenge.domain.util.Response
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.ConnectException

class FixtureViewModelTest {


    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: FixtureViewModel

    @MockK
    private lateinit var fetchFixtureUseCase: FetchFixtureUseCase

    private val fixture = ModelCreator.fixture.map { it.toMatch() }

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = FixtureViewModel(fetchFixtureUseCase)
    }

    @Test
    fun `should await results when fetch match result success`() = runTest {
        coEvery { fetchFixtureUseCase.execute(true) } returns Response.Success(fixture)
        viewModel.uiState.test {
            viewModel.fetchFixture(true)
            val loading = awaitItem()
            assertThat(loading, instanceOf(MatchScreenState.Loading::class.java))
            val success = awaitItem()
            assertThat(success, instanceOf(MatchScreenState.Success::class.java))
            val expectedData = fixture.map { it.toFixtureDisplayModel() }
            val data = (success as MatchScreenState.Success).data
            assertEquals(expectedData, data)
        }
    }

    @Test
    fun `should await error when fetch match result fail`() = runTest {
        val connectError = ConnectException()
        coEvery { fetchFixtureUseCase.execute(true) } returns Response.Error(connectError)
        viewModel.uiState.test {
            viewModel.fetchFixture(true)
            val loading = awaitItem()
            assertThat(loading, instanceOf(MatchScreenState.Loading::class.java))
            val error = awaitItem()
            assertThat(error, instanceOf(MatchScreenState.Error::class.java))
            val appError = (error as MatchScreenState.Error).error
            assertThat(appError, instanceOf(AppError.Connection::class.java))
        }
    }

}
