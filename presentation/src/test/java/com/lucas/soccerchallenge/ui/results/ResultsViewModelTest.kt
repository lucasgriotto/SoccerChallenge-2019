package com.lucas.soccerchallenge.ui.results

import app.cash.turbine.test
import com.lucas.soccerchallenge.ui.results.adapter.toResultDisplayModel
import com.lucas.soccerchallenge.utils.MainDispatcherRule
import com.lucas.soccerchallenge.utils.ModelCreator
import com.lucas.soccerchallenge.utils.Resource
import com.lucas.soccerchallenge.utils.errorfactory.AppError
import com.soccerchallenge.data.network.model.mapper.toMatch
import com.soccerchallenge.domain.usecase.FetchMatchResultsUseCase
import com.soccerchallenge.domain.util.Response
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsInstanceOf
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
        viewModel.results.test {
            viewModel.fetchMatchResults(true)
            val initialize = awaitItem()
            assertThat(initialize, IsInstanceOf.instanceOf(Resource.Initialize::class.java))
            val loading = awaitItem()
            assertThat(loading, IsInstanceOf.instanceOf(Resource.Loading::class.java))
            val success = awaitItem()
            assertThat(success, IsInstanceOf.instanceOf(Resource.Success::class.java))
            val expectedData = results.map { it.toResultDisplayModel() }
            val data = (success as Resource.Success).data
            assertEquals(expectedData, data)
        }
    }

    @Test
    fun `should await error when fetch match result fail`() = runTest {
        val connectError = ConnectException()
        coEvery { fetchMatchResultsUseCase.execute(true) } returns Response.Error(connectError)
        viewModel.results.test {
            viewModel.fetchMatchResults(true)
            val initialize = awaitItem()
            assertThat(initialize, IsInstanceOf.instanceOf(Resource.Initialize::class.java))
            val loading = awaitItem()
            assertThat(loading, IsInstanceOf.instanceOf(Resource.Loading::class.java))
            val error = awaitItem()
            assertThat(error, IsInstanceOf.instanceOf(Resource.Error::class.java))
            val appError = (error as Resource.Error).error
            assertThat(appError, IsInstanceOf.instanceOf(AppError.Connection::class.java))
        }
    }

}
