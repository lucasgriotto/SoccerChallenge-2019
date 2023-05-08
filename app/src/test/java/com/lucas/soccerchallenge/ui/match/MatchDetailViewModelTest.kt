package com.lucas.soccerchallenge.ui.match

import app.cash.turbine.test
import com.lucas.soccerchallenge.core.data.repository.SoccerRepository
import com.lucas.soccerchallenge.core.network.model.mapper.toMatch
import com.lucas.soccerchallenge.ui.base.Resource
import com.lucas.soccerchallenge.ui.fixture.adapter.toFixtureDisplayModel
import com.lucas.soccerchallenge.ui.match.usecase.FetchMatchUseCase
import com.lucas.soccerchallenge.ui.results.adapter.toResultDisplayModel
import com.lucas.soccerchallenge.utils.MainDispatcherRule
import com.lucas.soccerchallenge.utils.ModelCreator
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

class MatchDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: MatchDetailViewModel

    @MockK
    private lateinit var repository: SoccerRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        val fetchMatchUseCase = FetchMatchUseCase(repository)
        viewModel = MatchDetailViewModel(fetchMatchUseCase)
    }

    @Test
    fun `should await FixtureDisplayModel when fetchMatchUseCase returns a match fixture`() = runTest {
        val fixtureMatch = ModelCreator.fixture.first().toMatch()
        coEvery { repository.fetchMatch(fixtureMatch.id) } returns fixtureMatch
        viewModel.fixtureResponse.test {
            viewModel.fetchMatch(fixtureMatch.id)
            val initialize = awaitItem()
            MatcherAssert.assertThat(initialize, IsInstanceOf.instanceOf(Resource.Initialize::class.java))
            val success = awaitItem()
            MatcherAssert.assertThat(success, IsInstanceOf.instanceOf(Resource.Success::class.java))
            val data = (success as Resource.Success).data
            Assert.assertEquals(fixtureMatch.toFixtureDisplayModel(), data)
        }
    }

    @Test
    fun `should await ResultDisplayModel when fetchMatchUseCase returns a match result`() = runTest {
        val resultMatch = ModelCreator.results.first().toMatch()
        coEvery { repository.fetchMatch(resultMatch.id) } returns resultMatch
        viewModel.resultResponse.test {
            viewModel.fetchMatch(resultMatch.id)
            val initialize = awaitItem()
            MatcherAssert.assertThat(initialize, IsInstanceOf.instanceOf(Resource.Initialize::class.java))
            val success = awaitItem()
            MatcherAssert.assertThat(success, IsInstanceOf.instanceOf(Resource.Success::class.java))
            val data = (success as Resource.Success).data
            Assert.assertEquals(resultMatch.toResultDisplayModel(), data)
        }
    }

}
