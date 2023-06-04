package com.soccerchallenge.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.soccerchallenge.domain.model.Competition
import com.soccerchallenge.domain.model.MatchType
import com.soccerchallenge.domain.model.Score
import com.soccerchallenge.domain.model.Team
import java.util.*

@Entity(tableName = "matches")
data class MatchEntity(
    @PrimaryKey val id: Int,
    val type: MatchType,
    @Embedded(prefix = "home_team_")
    val homeTeam: Team,
    @Embedded(prefix = "away_team_")
    val awayTeam: Team,
    val date: Date,
    @Embedded(prefix = "competition_")
    val competition: Competition,
    val venueName: String,
    val state: String?,
    @Embedded(prefix = "score_")
    val score: Score?
)
