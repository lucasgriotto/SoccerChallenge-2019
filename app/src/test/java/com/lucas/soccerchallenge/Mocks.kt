package com.lucas.soccerchallenge

import com.lucas.soccerchallenge.data.entities.*
import com.lucas.soccerchallenge.data.model.Competition
import com.lucas.soccerchallenge.data.model.Match
import com.lucas.soccerchallenge.data.model.Team
import java.util.*

val MATCH1 = Match(
    homeTeam = Team("Chelsea"),
    awayTeam = Team("Boca Juniors"),
    date = Date(),
    competition = Competition(6, "UEFA Europa League"),
    venueName = "Stamford Bridge",
    state = "finished",
    score = null
)

val MATCH2 = Match(
    homeTeam = Team("Chelsea"),
    awayTeam = Team("Boca Juniors"),
    date = Date(),
    competition = Competition(1, "FA Cup"),
    venueName = "Stamford Bridge",
    state = "finished",
    score = null
)

val MATCH_ENTITY = MatchEntity(
    date = Date(),
    venue = VenueEntity("Stamford Bridge",0),
    score = ScoreEntity(1,"Chelsea",2),
    awayTeam = TeamEntity("Boca Juniors"),
    homeTeam = TeamEntity("Chelsea"),
    id = 1,
    state = "finished",
    competitionStage = CompetitionStageEntity(CompetitionEntity("FA Cup",1))
)