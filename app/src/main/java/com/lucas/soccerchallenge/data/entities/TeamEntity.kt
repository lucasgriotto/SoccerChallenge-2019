package com.lucas.soccerchallenge.data.entities

import com.google.gson.annotations.SerializedName

data class TeamEntity(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("alias")
	val alias: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("shortName")
	val shortName: String? = null,

	@field:SerializedName("abbr")
	val abbr: String? = null
)