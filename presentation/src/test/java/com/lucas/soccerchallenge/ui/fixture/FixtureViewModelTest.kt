package com.lucas.soccerchallenge.ui.fixture

import app.cash.turbine.test
import com.lucas.soccerchallenge.ui.fixture.adapter.toFixtureDisplayModel
import com.lucas.soccerchallenge.utils.MainDispatcherRule
import com.lucas.soccerchallenge.utils.ModelCreator
import com.lucas.soccerchallenge.utils.Resource
import com.lucas.soccerchallenge.utils.errorfactory.AppError
import com.soccerchallenge.data.network.model.mapper.toMatch
import com.soccerchallenge.domain.usecase.FetchFixtureUseCase
import com.soccerchallenge.domain.util.Response
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert
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
        viewModel.fixture.test {
            viewModel.fetchFixture(true)
            val initialize = awaitItem()
            MatcherAssert.assertThat(initialize, IsInstanceOf.instanceOf(Resource.Initialize::class.java))
            val loading = awaitItem()
            MatcherAssert.assertThat(loading, IsInstanceOf.instanceOf(Resource.Loading::class.java))
            val success = awaitItem()
            MatcherAssert.assertThat(success, IsInstanceOf.instanceOf(Resource.Success::class.java))
            val expectedData = fixture.map { it.toFixtureDisplayModel() }
            val data = (success as Resource.Success).data
            Assert.assertEquals(expectedData, data)
        }
    }

    @Test
    fun `should await error when fetch match result fail`() = runTest {
        val connectError = ConnectException()
        coEvery { fetchFixtureUseCase.execute(true) } returns Response.Error(connectError)
        viewModel.fixture.test {
            viewModel.fetchFixture(true)
            val initialize = awaitItem()
            MatcherAssert.assertThat(initialize, IsInstanceOf.instanceOf(Resource.Initialize::class.java))
            val loading = awaitItem()
            MatcherAssert.assertThat(loading, IsInstanceOf.instanceOf(Resource.Loading::class.java))
            val error = awaitItem()
            MatcherAssert.assertThat(error, IsInstanceOf.instanceOf(Resource.Error::class.java))
            val appError = (error as Resource.Error).error
            MatcherAssert.assertThat(appError, IsInstanceOf.instanceOf(AppError.Connection::class.java))
        }
    }

}
