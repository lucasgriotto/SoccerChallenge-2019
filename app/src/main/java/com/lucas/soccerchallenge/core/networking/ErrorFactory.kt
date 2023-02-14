package com.lucas.soccerchallenge.core.networking

import android.content.Context
import com.google.gson.JsonSyntaxException
import com.lucas.soccerchallenge.core.exceptions.NoBodyException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ErrorFactory {

    fun getError(context: Context, throwable: Throwable): AppError {
        return when (throwable) {
            is JsonSyntaxException -> AppError.JsonConverter(context)
            is NoBodyException -> AppError.EmptyBody(context)
            is UnknownHostException, is SocketTimeoutException, is ConnectException -> AppError.Connection(context)
            else -> AppError.Error(context)
        }
    }

}
