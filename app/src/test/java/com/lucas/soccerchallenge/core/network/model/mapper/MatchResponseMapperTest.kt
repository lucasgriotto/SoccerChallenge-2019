package com.lucas.soccerchallenge.core.network.model.mapper

import com.lucas.soccerchallenge.core.model.Competition
import com.lucas.soccerchallenge.core.model.Match
import com.lucas.soccerchallenge.core.model.Score
import com.lucas.soccerchallenge.core.model.Team
import com.lucas.soccerchallenge.utils.ModelCreator
import org.junit.Assert.assertEquals
import org.junit.Test

class MatchResponseMapperTest {

    @Test
    fun `should get right match when mapping fixture response`() {
        val matchResponse = ModelCreator.fixture.first()
        val expectedMatch = Match(
            matchResponse.id,
            Team(matchResponse.homeTeam.name),
            Team(matchResponse.awayTeam.name),
            matchResponse.date,
            Competition(matchResponse.competitionStage.competition.id, matchResponse.competitionStage.competition.name),
            matchResponse.venue.name,
            matchResponse.state,
            null
        )
        val match = matchResponse.toMatch()
        assertEquals(expectedMatch, match)
    }


    @Test
    fun `should get right match when mapping result response`() {
        val matchResponse = ModelCreator.results.first()
        val expectedMatch = Match(
            matchResponse.id,
            Team(matchResponse.homeTeam.name),
            Team(matchResponse.awayTeam.name),
            matchResponse.date,
            Competition(matchResponse.competitionStage.competition.id, matchResponse.competitionStage.competition.name),
            matchResponse.venue.name,
            matchResponse.state,
            matchResponse.score?.let { Score(it.home, it.away, it.winner) }
        )
        val match = matchResponse.toMatch()
        assertEquals(expectedMatch, match)
    }

}