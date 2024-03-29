package com.soccerchallenge.data.network.model

import com.google.gson.annotations.SerializedName

data class VenueResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("id")
    val id: Int? = null
)
