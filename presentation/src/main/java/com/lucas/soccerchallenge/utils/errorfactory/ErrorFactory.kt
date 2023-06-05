package com.lucas.soccerchallenge.utils.errorfactory

import com.google.gson.JsonSyntaxException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorFactory {

    fun getError(throwable: Throwable): AppError {
        return when (throwable) {
            is JsonSyntaxException -> AppError.JsonConversion()
            is UnknownHostException, is SocketTimeoutException, is ConnectException -> AppError.Connection()
            else -> AppError.GeneralError()
        }
    }

}