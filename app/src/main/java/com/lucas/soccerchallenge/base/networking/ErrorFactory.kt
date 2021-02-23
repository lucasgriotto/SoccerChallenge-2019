package com.lucas.soccerchallenge.base.networking

import android.content.Context
import com.lucas.soccerchallenge.base.networking.exception.NoInternetException
import com.lucas.soccerchallenge.base.networking.exception.NoNetworkException
import java.net.SocketTimeoutException

object ErrorFactory {

    fun getError(context: Context, throwable: Throwable): AppError {
        return when (throwable) {
            is SocketTimeoutException -> AppError.Timeout(context)
            is NoInternetException -> AppError.NoInternet(context)
            is NoNetworkException -> AppError.NoNetwork(context)
            else -> AppError.Error(context)
        }
    }

}
