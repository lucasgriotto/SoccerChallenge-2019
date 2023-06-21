package com.lucas.soccerchallenge.ui.home.match

import app.cash.turbine.test
import com.lucas.soccerchallenge.ui.home.matchfilter.MatchFilterViewModel
import com.lucas.soccerchallenge.ui.results.adapter.ResultDisplayModel
import com.lucas.soccerchallenge.ui.results.adapter.toResultDisplayModel
import com.lucas.soccerchallenge.utils.ModelCreator
import com.soccerchallenge.data.network.model.mapper.toMatch
import com.soccerchallenge.data.util.CompetitionFilters
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.anyOf
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MatchFilterViewModelTest {


    private lateinit var viewModel: MatchFilterViewModel
    private val matches = ModelCreator.results.map { it.toMatch() }.map { it.toResultDisplayModel() }

    companion object {
        private const val PREMIER_LEAGUE = "Premier League"
        private const val FA_CUP = "FA Cup"
        private const val CARABAO_CUP = "Carabao Cup"
    }

    @Before
    fun setUp() {
        viewModel = MatchFilterViewModel(UnconfinedTestDispatcher())
    }

    @Test
    fun `should return only Premier League matches when that filter is set`() = runTest {
        CompetitionFilters.competitionFilter.find { it.name == PREMIER_LEAGUE }?.also { competition ->
            val filters = hashSetOf(competition.id)
            viewModel.filterMatches(matches, filters)
            viewModel.filteredMatches.test {
                val data = awaitItem()
                data.forEach { displayModel ->
                    if (displayModel is ResultDisplayModel) {
                        assertEquals(competition.name, displayModel.competitionName)
                    }
                }
            }
        } ?: run {
            competitionNotFound()
        }
    }

    @Test
    fun `should return only FA Cup matches when that filter is set`() = runTest {
        CompetitionFilters.competitionFilter.find { it.name == FA_CUP }?.also { competition ->
            val filters = hashSetOf(competition.id)
            viewModel.filterMatches(matches, filters)
            viewModel.filteredMatches.test {
                val data = awaitItem()
                data.forEach { displayModel ->
                    if (displayModel is ResultDisplayModel) {
                        assertEquals(competition.name, displayModel.competitionName)
                    }
                }
            }
        } ?: run {
            competitionNotFound()
        }
    }

    @Test
    fun `should return only Carabao Cup matches when that filter is set`() = runTest {
        CompetitionFilters.competitionFilter.find { it.name == CARABAO_CUP }?.also { competition ->
            val filters = hashSetOf(competition.id)
            viewModel.filterMatches(matches, filters)
            viewModel.filteredMatches.test {
                val data = awaitItem()
                data.forEach { displayModel ->
                    if (displayModel is ResultDisplayModel) {
                        assertEquals(competition.name, displayModel.competitionName)
                    }
                }
            }
        } ?: run {
            competitionNotFound()
        }
    }

    @Test
    fun `should return only Carabao Cup and FA Cup matches when those filters are set`() = runTest {
        CompetitionFilters.competitionFilter.filter { it.name == CARABAO_CUP || it.name == FA_CUP }
                .also { competitions ->
                    if (competitions.isEmpty()) {
                        competitionNotFound()
                    } else {
                        val filters = competitions.map { it.id }.toHashSet()
                        viewModel.filterMatches(matches, filters)
                        viewModel.filteredMatches.test {
                            val data = awaitItem()
                            data.forEach { displayModel ->
                                if (displayModel is ResultDisplayModel) {
                                    assertThat(
                                            displayModel.competitionName,
                                            anyOf(
                                                    equalTo(CARABAO_CUP),
                                                    equalTo(FA_CUP)
                                            )
                                    )
                                }
                            }
                        }
                    }
                }
    }

    private fun competitionNotFound() {
        fail("Competitions not found")
    }

}
