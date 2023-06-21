package com.soccerchallenge.domain.model

data class Score(
        val home: Int,
        val away: Int,
        val winner: String?
)