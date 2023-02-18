package com.lucas.soccerchallenge.core.networking

import com.google.gson.JsonSyntaxException
import com.lucas.soccerchallenge.core.exceptions.NoBodyException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorFactory {

    fun getError(throwable: Throwable): AppError {
        return when (throwable) {
            is JsonSyntaxException -> AppError.JsonConverter()
            is NoBodyException -> AppError.EmptyBody()
            is UnknownHostException, is SocketTimeoutException, is ConnectException -> AppError.Connection()
            else -> AppError.Error()
        }
    }

}
