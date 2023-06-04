package com.soccerchallenge.data.repository.util

import com.soccerchallenge.domain.util.Response

suspend fun <T> safeRepositoryCall(block: suspend () -> T): Response<T> {
    return try {
        Response.Success(block())
    } catch (e: Throwable) {
        Response.Error(e)
    }
}
