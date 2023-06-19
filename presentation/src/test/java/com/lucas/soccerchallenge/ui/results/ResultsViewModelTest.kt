package com.lucas.soccerchallenge.ui.results

import app.cash.turbine.test
import com.lucas.soccerchallenge.ui.home.match.MatchScreenState
import com.lucas.soccerchallenge.ui.results.adapter.toResultDisplayModel
import com.lucas.soccerchallenge.utils.MainDispatcherRule
import com.lucas.soccerchallenge.utils.ModelCreator
import com.lucas.soccerchallenge.utils.errorfactory.AppError
import com.soccerchallenge.data.network.model.mapper.toMatch
import com.soccerchallenge.domain.usecase.FetchMatchResultsUseCase
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

class ResultsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ResultsViewModel

    @MockK
    private lateinit var fetchMatchResultsUseCase: FetchMatchResultsUseCase

    private val results = ModelCreator.results.map { it.toMatch() }

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = ResultsViewModel(fetchMatchResultsUseCase)
    }

    @Test
    fun `should await results when fetch match result success`() = runTest {
        coEvery { fetchMatchResultsUseCase.execute(true) } returns Response.Success(results)
        viewModel.uiState.test {
            viewModel.fetchMatchResults(true)
            val loading = awaitItem()
            assertThat(loading, instanceOf(MatchScreenState.Loading::class.java))
            val success = awaitItem()
            assertThat(success, instanceOf(MatchScreenState.Success::class.java))
            val expectedData = results.map { it.toResultDisplayModel() }
            val data = (success as MatchScreenState.Success).data
            assertEquals(expectedData, data)
        }
    }

    @Test
    fun `should await error when fetch match result fail`() = runTest {
        val connectError = ConnectException()
        coEvery { fetchMatchResultsUseCase.execute(true) } returns Response.Error(connectError)
        viewModel.uiState.test {
            viewModel.fetchMatchResults(true)
            val loading = awaitItem()
            assertThat(loading, instanceOf(MatchScreenState.Loading::class.java))
            val error = awaitItem()
            assertThat(error, instanceOf(MatchScreenState.Error::class.java))
            val appError = (error as MatchScreenState.Error).error
            assertThat(appError, instanceOf(AppError.Connection::class.java))
        }
    }

}
