package com.soccerchallenge.data.network.model

import com.google.gson.annotations.SerializedName

enum class MatchTypeResponse {
    @SerializedName("FixtureUpcoming")
    FIXTURE,

    @SerializedName("FixtureFinal")
    RESULT
}