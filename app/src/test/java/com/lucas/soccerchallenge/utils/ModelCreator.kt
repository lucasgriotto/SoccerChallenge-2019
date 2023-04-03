package com.lucas.soccerchallenge.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lucas.soccerchallenge.core.network.model.MatchResponse

object ModelCreator {

    private val gson by lazy { Gson() }

    val fixture: List<MatchResponse> by lazy {
        val type = object : TypeToken<List<MatchResponse>>() {}.type
        gson.fromJson(
            TestUtils.jsonToString("fixtures.json"),
            type
        )
    }

    val results: List<MatchResponse> by lazy {
        val type = object : TypeToken<List<MatchResponse>>() {}.type
        gson.fromJson(
            TestUtils.jsonToString("results.json"),
            type
        )
    }

}