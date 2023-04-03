package com.lucas.soccerchallenge.utils.errorfactory

import android.content.Context
import androidx.annotation.StringRes
import com.lucas.soccerchallenge.R

sealed class AppError(
    private val message: String? = null,
    @StringRes private val fallbackMessage: Int = R.string.error_unknown
) {
    class Connection : AppError(fallbackMessage = R.string.error_connection)
    class JsonConversion : AppError(fallbackMessage = R.string.error_json)
    class EmptyBody : AppError(fallbackMessage = R.string.error_empty)
    class GeneralError : AppError(fallbackMessage = R.string.error_general)

    fun getErrorMessage(context: Context): String {
        return message ?: context.getString(fallbackMessage)
    }

}