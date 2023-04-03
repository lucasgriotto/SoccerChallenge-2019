package com.lucas.soccerchallenge.core.network.model

import com.google.gson.annotations.SerializedName

data class CompetitionResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("id")
    val id: Int
)
