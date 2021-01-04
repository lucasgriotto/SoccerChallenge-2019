package com.lucas.soccerchallenge.base.networking

import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.SoccerChallengeApp
import java.io.IOException

sealed class AppError(message: String) : IOException(message) {
    class NoNetwork : AppError(SoccerChallengeApp.instance.getString(R.string.error_network))
    class NoInternet : AppError(SoccerChallengeApp.instance.getString(R.string.error_no_internet))
    class Timeout : AppError(SoccerChallengeApp.instance.getString(R.string.error_timeout))
    class EmptyBody : AppError(SoccerChallengeApp.instance.getString(R.string.error_empty))
    class GeneralError(error: String?) :
        AppError(error ?: SoccerChallengeApp.instance.getString(R.string.error_unknown))
    class JsonConverter : AppError(SoccerChallengeApp.instance.getString(R.string.error_json))
}