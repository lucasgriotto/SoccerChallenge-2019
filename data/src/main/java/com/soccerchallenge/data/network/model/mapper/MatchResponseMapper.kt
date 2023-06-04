package com.soccerchallenge.data.network.model.mapper

import com.soccerchallenge.data.network.model.MatchResponse
import com.soccerchallenge.data.network.model.MatchTypeResponse
import com.soccerchallenge.domain.model.Competition
import com.soccerchallenge.domain.model.Match
import com.soccerchallenge.domain.model.MatchType
import com.soccerchallenge.domain.model.Score
import com.soccerchallenge.domain.model.Team

fun MatchResponse.toMatch(): Match {
    val matchType = when (type) {
        MatchTypeResponse.FIXTURE -> MatchType.FIXTURE
        MatchTypeResponse.RESULT -> MatchType.RESULT
    }
    return Match(
        id,
        matchType,
        Team(homeTeam.id, homeTeam.name),
        Team(awayTeam.id, awayTeam.name),
        date,
        Competition(
            competitionStage.competition.id,
            competitionStage.competition.name
        ),
        venue.name,
        state,
        score?.let { Score(it.home, it.away, it.winner) }
    )
}