package com.lucas.soccerchallenge.utils

import java.io.BufferedReader
import java.io.InputStreamReader

object TestUtils {

    fun jsonToString(fileName: String): String {
        return StringBuilder().also { builder ->
            val bufferedReader = BufferedReader(
                InputStreamReader(javaClass.getResource("/$fileName")?.openStream())
            )
            bufferedReader.use { builder.append(it.readText()) }
        }.toString()
    }

}