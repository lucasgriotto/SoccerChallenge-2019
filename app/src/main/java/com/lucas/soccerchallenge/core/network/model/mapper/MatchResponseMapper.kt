package com.lucas.soccerchallenge.core.network.model.mapper

import com.lucas.soccerchallenge.core.model.Competition
import com.lucas.soccerchallenge.core.model.Match
import com.lucas.soccerchallenge.core.model.Score
import com.lucas.soccerchallenge.core.model.Team
import com.lucas.soccerchallenge.core.network.model.MatchResponse

fun MatchResponse.toMatch() = Match(
    id,
    Team(homeTeam.name),
    Team(awayTeam.name),
    date,
    Competition(competitionStage.competition.id, competitionStage.competition.name),
    venue.name,
    state,
    score?.let { Score(it.home, it.away, it.winner) }
)