package com.lucas.soccerchallenge.features.main.filter

import com.lucas.soccerchallenge.data.model.Match
import com.lucas.soccerchallenge.data.toMatch
import com.lucas.soccerchallenge.utils.ModelCreator
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class MatchFilterTest {

    private lateinit var matchFilter: MatchFilter

    lateinit var results: List<Match>

    companion object {
        private const val AMOUNT_PREMIER_LEAGUE = 9
        private const val AMOUNT_FA_CUP = 2
        private const val AMOUNT_CARABAO_CUP = 3
    }

    @Before
    fun setUp() {
        matchFilter = MatchFilter()
        results = ModelCreator.results.map { it.toMatch() }
        matchFilter.setList(results)
    }

    @Test
    fun `filter Premier League`() {
        val competition = Filters.competitionFilter.find { it.name == "Premier League" }
        competition?.let { c ->
            matchFilter.filters = hashSetOf(c)
            assertEquals(AMOUNT_PREMIER_LEAGUE, matchFilter.filteredList.size)
            assertEquals(
                AMOUNT_PREMIER_LEAGUE,
                matchFilter.filteredList.filter { it.competition == competition }.size
            )
        } ?: run {
            fail("Competition not found")
        }
    }

    @Test
    fun `filter FA Cup`() {
        val competition = Filters.competitionFilter.find { it.name == "FA Cup" }
        competition?.let { c ->
            matchFilter.filters = hashSetOf(c)
            assertEquals(AMOUNT_FA_CUP, matchFilter.filteredList.size)
            assertEquals(
                AMOUNT_FA_CUP,
                matchFilter.filteredList.filter { it.competition == competition }.size
            )
        } ?: run {
            fail("Competition not found")
        }
    }

    @Test
    fun `filter Carabao Cup`() {
        val competition = Filters.competitionFilter.find { it.name == "Carabao Cup" }
        competition?.let { c ->
            matchFilter.filters = hashSetOf(c)
            assertEquals(AMOUNT_CARABAO_CUP, matchFilter.filteredList.size)
            assertEquals(
                AMOUNT_CARABAO_CUP,
                matchFilter.filteredList.filter { it.competition == competition }.size
            )
        } ?: run {
            fail("Competition not found")
        }
    }

    @Test
    fun `filter Carabao Cup && FA Cup`() {
        val carabao = Filters.competitionFilter.find { it.name == "Carabao Cup" }
        val facup = Filters.competitionFilter.find { it.name == "FA Cup" }
        val totalAmount = AMOUNT_CARABAO_CUP + AMOUNT_FA_CUP
        if (carabao != null && facup != null) {
            matchFilter.filters = hashSetOf(carabao, facup)
            assertEquals(totalAmount, matchFilter.filteredList.size)
            assertEquals(
                totalAmount,
                matchFilter.filteredList.filter { it.competition == carabao || it.competition == facup }.size
            )
        } else {
            fail("Competition not found")
        }
    }

    @Test
    fun `filter All`() {
        matchFilter.filters = hashSetOf(Filters.allFilterCompetition)
        assertEquals(results.size, matchFilter.filteredList.size)
    }

}