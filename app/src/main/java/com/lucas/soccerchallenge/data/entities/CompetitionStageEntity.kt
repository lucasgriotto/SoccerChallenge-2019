package com.lucas.soccerchallenge.data.entities

import com.google.gson.annotations.SerializedName

data class CompetitionStageEntity(

	@field:SerializedName("competition")
	val competition: CompetitionEntity
)