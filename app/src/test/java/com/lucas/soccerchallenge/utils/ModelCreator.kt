package com.lucas.soccerchallenge.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lucas.soccerchallenge.data.MatchEntity

object ModelCreator {

    private val gson by lazy { Gson() }

    val fixture: List<MatchEntity> by lazy {
        val type = object : TypeToken<List<MatchEntity>>() {}.type
        gson.fromJson(
            TestUtils.jsonToString("fixtures.json"),
            type
        )
    }

    val results: List<MatchEntity> by lazy {
        val type = object : TypeToken<List<MatchEntity>>() {}.type
        gson.fromJson(
            TestUtils.jsonToString("results.json"),
            type
        )
    }

}