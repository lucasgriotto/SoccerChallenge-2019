package com.lucas.soccerchallenge.core.network.model

import com.google.gson.annotations.SerializedName

data class TeamResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("alias")
    val alias: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("shortName")
    val shortName: String? = null,
    @SerializedName("abbr")
    val abbr: String? = null
)
