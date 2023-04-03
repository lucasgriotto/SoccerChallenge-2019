package com.lucas.soccerchallenge.core.network.utils

import com.lucas.soccerchallenge.core.network.exceptions.NullBodyException
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T> safeApiCall(networkCall: suspend () -> Response<T>): T {
    val response = networkCall.invoke()
    if (response.isSuccessful) {
        response.body()?.let {
            return it
        } ?: throw NullBodyException()
    } else {
        throw HttpException(response)
    }
}
