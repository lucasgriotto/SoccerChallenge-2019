package com.lucas.soccerchallenge.data.entity

import com.google.gson.annotations.SerializedName

data class VenueEntity(
    @SerializedName("name")
    val name: String,
    @SerializedName("id")
    val id: Int? = null
)
