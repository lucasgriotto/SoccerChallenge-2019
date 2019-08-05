package com.lucas.soccerchallenge.data.entities

import com.google.gson.annotations.SerializedName

data class VenueEntity(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int? = null
)