package com.lucas.soccerchallenge.core.extension

import com.lucas.soccerchallenge.core.exceptions.NoBodyException
import retrofit2.HttpException
import retrofit2.Response

internal suspend fun <T> suspendApiCallWrapper(networkCall: suspend () -> Response<T>): T {
    val response = networkCall.invoke()
    if (response.isSuccessful) {
        response.body()?.let {
            return it
        } ?: throw NoBodyException()
    } else {
        throw HttpException(response)
    }
}
