package com.lucas.soccerchallenge.base.networking

import android.content.Context
import com.lucas.soccerchallenge.R

sealed class AppError(val message: String) {
    class NoNetwork(context: Context) : AppError(context.getString(R.string.error_network))
    class NoInternet(context: Context) : AppError(context.getString(R.string.error_no_internet))
    class Timeout(context: Context) : AppError(context.getString(R.string.error_timeout))
    class Error(context: Context) : AppError(context.getString(R.string.error_general))
}