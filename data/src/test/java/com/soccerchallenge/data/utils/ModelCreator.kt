package com.soccerchallenge.data.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.soccerchallenge.data.network.model.MatchResponse
import java.io.BufferedReader
import java.io.InputStreamReader

object ModelCreator {

    private val gson by lazy { Gson() }

    val fixture: List<MatchResponse> by lazy {
        val type = object : TypeToken<List<MatchResponse>>() {}.type
        gson.fromJson(
                jsonToString("fixtures.json"),
                type
        )
    }

    val results: List<MatchResponse> by lazy {
        val type = object : TypeToken<List<MatchResponse>>() {}.type
        gson.fromJson(
                jsonToString("results.json"),
                type
        )
    }

    private fun jsonToString(fileName: String): String {
        return StringBuilder().also { builder ->
            val bufferedReader = BufferedReader(
                    InputStreamReader(javaClass.getResource("/$fileName")?.openStream())
            )
            bufferedReader.use { builder.append(it.readText()) }
        }.toString()
    }

}
