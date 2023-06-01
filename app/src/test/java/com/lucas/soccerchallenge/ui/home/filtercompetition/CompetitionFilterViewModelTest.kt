package com.lucas.soccerchallenge.ui.home.filtercompetition

import app.cash.turbine.test
import com.lucas.soccerchallenge.core.data.repository.UserPreferencesRepository
import com.lucas.soccerchallenge.ui.home.competitionfilter.CompetitionFilterViewModel
import com.lucas.soccerchallenge.ui.home.competitionfilter.CompetitionFilters
import com.lucas.soccerchallenge.ui.home.competitionfilter.model.toFilterCompetitionDisplayModel
import com.lucas.soccerchallenge.utils.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
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

    @MockK
    private lateinit var userPreferencesRepository: UserPreferencesRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = CompetitionFilterViewModel(userPreferencesRepository)
    }

    @Test
    fun `should await all option filter when none option is selected`() = runTest {
        val filters = CompetitionFilters.competitionFilter.map { competition ->
            competition.toFilterCompetitionDisplayModel(isSelected = false)
        }
        val expectedFilter = setOf(CompetitionFilters.allFilterCompetition)
        viewModel.filter.test {
            viewModel.updateFilters(filters)
            assertEquals(filters, viewModel.allFilterCompetitionDisplayModels)
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
            assertEquals(filters, viewModel.allFilterCompetitionDisplayModels)
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
            assertEquals(filters, viewModel.allFilterCompetitionDisplayModels)
            assertEquals(CompetitionFilters.defaultSelectedCompetition, viewModel.selectedFilters)
            val filter = awaitItem()
            assertEquals(CompetitionFilters.defaultSelectedCompetition, filter)
        }
    }

}
