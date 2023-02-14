package com.lucas.soccerchallenge.core.networking

import android.content.Context
import com.lucas.soccerchallenge.R

sealed class AppError(val message: String) {
    class Connection(context: Context) : AppError(context.getString(R.string.error_connection))
    class JsonConverter(context: Context) : AppError(context.getString(R.string.error_json))
    class EmptyBody(context: Context) : AppError(context.getString(R.string.error_empty))
    class Error(context: Context) : AppError(context.getString(R.string.error_general))
}