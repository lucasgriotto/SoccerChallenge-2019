package com.lucas.soccerchallenge.features.home.filtercompetition

import app.cash.turbine.test
import com.lucas.soccerchallenge.features.home.competitionfilter.CompetitionFilterViewModel
import com.lucas.soccerchallenge.features.home.competitionfilter.CompetitionFilters
import com.lucas.soccerchallenge.features.home.competitionfilter.model.toFilterCompetitionDisplayModel
import com.lucas.soccerchallenge.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CompetitionFilterViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CompetitionFilterViewModel

    @Before
    fun setUp() {
        viewModel = CompetitionFilterViewModel()
    }

    @Test
    fun `should await all option filter when none option is selected`() = runTest {
        val filters = CompetitionFilters.competitionFilter.map { competition ->
            competition.toFilterCompetitionDisplayModel(isSelected = false)
        }
        val expectedFilter = setOf(CompetitionFilters.allFilterCompetition)
        viewModel.filter.test {
            viewModel.updateFilters(filters)
            assertEquals(filters, viewModel.allFilters)
            assertEquals(expectedFilter, viewModel.selectedFilters)
            val filter = awaitItem()
            assertEquals(expectedFilter, filter)
        }
    }

    @Test
    fun `should await all option filter when all competitions are selected but all option is not`() = runTest {
        val filters = CompetitionFilters.competitionFilter.map { competition ->
            competition.toFilterCompetitionDisplayModel(
                isSelected = CompetitionFilters.allFilterCompetition.id != competition.id
            )
        }
        val expectedFilter = setOf(CompetitionFilters.allFilterCompetition)
        viewModel.filter.test {
            viewModel.updateFilters(filters)
            assertEquals(filters, viewModel.allFilters)
            assertEquals(expectedFilter, viewModel.selectedFilters)
            val filter = awaitItem()
            assertEquals(expectedFilter, filter)
        }
    }

    @Test
    fun `should await default filters when default filter is selected`() = runTest {
        val filters = CompetitionFilters.competitionFilter.map { competition ->
            competition.toFilterCompetitionDisplayModel(
                isSelected = CompetitionFilters.defaultSelectedCompetition.contains(competition)
            )
        }
        viewModel.filter.test {
            viewModel.updateFilters(filters)
            assertEquals(filters, viewModel.allFilters)
            assertEquals(CompetitionFilters.defaultSelectedCompetition, viewModel.selectedFilters)
            val filter = awaitItem()
            assertEquals(CompetitionFilters.defaultSelectedCompetition, filter)
        }
    }

}
