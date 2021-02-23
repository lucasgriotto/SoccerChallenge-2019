package com.lucas.soccerchallenge.base.extension

import com.lucas.soccerchallenge.base.networking.exception.NoBodyException
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T> suspendApiCallWrapper(f: suspend () -> Response<T>): T {
    val response = f.invoke()
    if (response.isSuccessful) {
        response.body()?.let {
            return it
        } ?: throw NoBodyException()
    } else {
        throw HttpException(response)
    }
}
 