package com.soccerchallenge.data.database.entity.mapper

import com.soccerchallenge.data.database.entity.MatchEntity
import com.soccerchallenge.domain.model.Match

object MatchEntityMapper : EntityMapper<Match, MatchEntity> {

    override fun asDomain(entity: MatchEntity) = Match(
        id = entity.id,
        type = entity.type,
        date = entity.date,
        venueName = entity.venueName,
        awayTeam = entity.awayTeam,
        homeTeam = entity.homeTeam,
        competition = entity.competition,
        score = entity.score,
        state = entity.state
    )

    override fun asEntity(domain: Match) = MatchEntity(
        id = domain.id,
        state = domain.state,
        date = domain.date,
        type = domain.type,
        venueName = domain.venueName,
        score = domain.score,
        competition = domain.competition,
        homeTeam = domain.homeTeam,
        awayTeam = domain.awayTeam
    )

}
