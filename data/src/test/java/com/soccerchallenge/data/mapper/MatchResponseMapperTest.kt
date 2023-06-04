package com.soccerchallenge.data.mapper

import com.soccerchallenge.data.network.model.mapper.toMatch
import com.soccerchallenge.data.utils.ModelCreator
import com.soccerchallenge.domain.model.Competition
import com.soccerchallenge.domain.model.Match
import com.soccerchallenge.domain.model.MatchType
import com.soccerchallenge.domain.model.Score
import com.soccerchallenge.domain.model.Team
import org.junit.Assert.assertEquals
import org.junit.Test

class MatchResponseMapperTest {

    @Test
    fun `should get right match when mapping fixture response`() {
        val matchResponse = ModelCreator.fixture.first()
        val expectedMatch = Match(
            matchResponse.id,
            MatchType.FIXTURE,
            Team(matchResponse.homeTeam.id, matchResponse.homeTeam.name),
            Team(matchResponse.awayTeam.id, matchResponse.awayTeam.name),
            matchResponse.date,
            Competition(
                matchResponse.competitionStage.competition.id,
                matchResponse.competitionStage.competition.name
            ),
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
            MatchType.RESULT,
            Team(matchResponse.homeTeam.id, matchResponse.homeTeam.name),
            Team(matchResponse.awayTeam.id, matchResponse.awayTeam.name),
            matchResponse.date,
            Competition(
                matchResponse.competitionStage.competition.id,
                matchResponse.competitionStage.competition.name
            ),
            matchResponse.venue.name,
            matchResponse.state,
            matchResponse.score?.let { Score(it.home, it.away, it.winner) }
        )
        val match = matchResponse.toMatch()
        assertEquals(expectedMatch, match)
    }

}