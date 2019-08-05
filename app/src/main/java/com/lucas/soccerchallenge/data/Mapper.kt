package com.lucas.soccerchallenge.data

import com.lucas.soccerchallenge.data.entities.MatchEntity
import com.lucas.soccerchallenge.data.model.Competition
import com.lucas.soccerchallenge.data.model.Match
import com.lucas.soccerchallenge.data.model.Score
import com.lucas.soccerchallenge.data.model.Team

fun MatchEntity.toMatch() = Match(
    Team(homeTeam.name),
    Team(awayTeam.name),
    date,
    Competition(competitionStage.competition.id, competitionStage.competition.name),
    venue.name,
    state,
    score?.let { Score(it.home, it.away, it.winner) }
)