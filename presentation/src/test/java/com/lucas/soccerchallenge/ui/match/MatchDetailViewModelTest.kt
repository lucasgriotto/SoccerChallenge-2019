package com.lucas.soccerchallenge.ui.match

import app.cash.turbine.test
import com.lucas.soccerchallenge.ui.fixture.adapter.toFixtureDisplayModel
import com.lucas.soccerchallenge.ui.results.adapter.toResultDisplayModel
import com.lucas.soccerchallenge.utils.MainDispatcherRule
import com.lucas.soccerchallenge.utils.ModelCreator
import com.soccerchallenge.data.network.model.mapper.toMatch
import com.soccerchallenge.domain.usecase.FetchMatchUseCase
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

class MatchDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: MatchDetailViewModel

    @MockK
    private lateinit var fetchMatchUseCase: FetchMatchUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = MatchDetailViewModel(fetchMatchUseCase)
    }

    @Test
    fun `should await result when fetch match is result type and success`() = runTest {
        val match = ModelCreator.results.map { it.toMatch() }.first()
        val expectedData = match.toResultDisplayModel()
        coEvery { fetchMatchUseCase.execute(match.id) } returns Response.Success(match)
        viewModel.uiState.test {
            viewModel.fetchMatch(match.id)
            val idle = awaitItem()
            assertThat(idle, instanceOf(MatchDetailScreenState.Idle::class.java))
            val success = awaitItem()
            assertThat(success, instanceOf(MatchDetailScreenState.ResultData::class.java))
            val data = (success as MatchDetailScreenState.ResultData).data
            assertEquals(expectedData, data)
        }
    }

    @Test
    fun `should await fixture when fetch match is fixture type and success`() = runTest {
        val match = ModelCreator.fixture.map { it.toMatch() }.first()
        val expectedData = match.toFixtureDisplayModel()
        coEvery { fetchMatchUseCase.execute(match.id) } returns Response.Success(match)
        viewModel.uiState.test {
            viewModel.fetchMatch(match.id)
            val idle = awaitItem()
            assertThat(idle, instanceOf(MatchDetailScreenState.Idle::class.java))
            val success = awaitItem()
            assertThat(success, instanceOf(MatchDetailScreenState.FixtureData::class.java))
            val data = (success as MatchDetailScreenState.FixtureData).data
            assertEquals(expectedData, data)
        }
    }

}
