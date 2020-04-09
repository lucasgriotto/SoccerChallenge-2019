package com.lucas.soccerchallenge

import com.lucas.soccerchallenge.data.model.Competition
import com.lucas.soccerchallenge.features.filter.MatchFilter
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MatchFilterTest {

    private lateinit var matchFilter: MatchFilter



    @Before
    fun init() {
        matchFilter = MatchFilter()
        val list = listOf(MATCH1, MATCH2)
        matchFilter.setList(list)
    }

    @Test
    fun filterFACup() {
        matchFilter.filters = hashSetOf(Competition(1, "FA Cup"))
        assertEquals(matchFilter.filteredList.size, 1)
        assertEquals(matchFilter.filteredList.contains(MATCH2), true)
    }

    @Test
    fun filterUEFACup() {
        matchFilter.filters = hashSetOf(Competition(6, "UEFA Europa League"))
        assertEquals(matchFilter.filteredList.size, 1)
        assertEquals(matchFilter.filteredList.contains(MATCH1), true)
    }
}