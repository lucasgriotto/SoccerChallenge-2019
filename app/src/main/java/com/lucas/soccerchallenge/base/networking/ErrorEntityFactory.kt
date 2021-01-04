package com.lucas.soccerchallenge.base.networking

import com.google.gson.JsonSyntaxException
import java.net.SocketTimeoutException

object ErrorEntityFactory {

    fun getError(throwable: Throwable): AppError {
        return when (throwable) {
            is SocketTimeoutException -> AppError.Timeout()
            is JsonSyntaxException -> AppError.JsonConverter()
            else -> AppError.GeneralError(throwable.message)
        }
    }

}