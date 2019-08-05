package com.lucas.soccerchallenge.data.entities

import com.google.gson.annotations.SerializedName

data class CompetitionEntity(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)