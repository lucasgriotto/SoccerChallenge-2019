package com.lucas.soccerchallenge

import com.lucas.soccerchallenge.data.model.Competition
import com.lucas.soccerchallenge.data.model.Match
import com.lucas.soccerchallenge.data.model.Team
import com.lucas.soccerchallenge.features.main.filter.MatchFilter
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class MatchFilterTest {

    private lateinit var matchFilter: MatchFilter

    private val match1 = Match(
        homeTeam = Team("Chelsea"),
        awayTeam = Team("Boca Juniors"),
        date = Date(),
        competition = Competition(6, "UEFA Europa League"),
        venueName = "Stamford Bridge",
        state = "finished",
        score = null
    )

    private val match2 = Match(
        homeTeam = Team("Chelsea"),
        awayTeam = Team("Boca Juniors"),
        date = Date(),
        competition = Competition(1, "FA Cup"),
        venueName = "Stamford Bridge",
        state = "finished",
        score = null
    )

    @Before
    fun init() {
        matchFilter = MatchFilter()
        val list = listOf(match1, match2)
        matchFilter.setList(list)
    }

    @Test
    fun filterFACup() {
        matchFilter.filters = hashSetOf(Competition(1, "FA Cup"))
        assertEquals(matchFilter.filteredList.size, 1)
        assertEquals(matchFilter.filteredList.contains(match2), true)
    }

    @Test
    fun filterUEFACup() {
        matchFilter.filters = hashSetOf(Competition(6, "UEFA Europa League"))
        assertEquals(matchFilter.filteredList.size, 1)
        assertEquals(matchFilter.filteredList.contains(match1), true)
    }
}