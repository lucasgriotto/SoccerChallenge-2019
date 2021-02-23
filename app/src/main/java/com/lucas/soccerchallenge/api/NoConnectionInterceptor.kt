package com.lucas.soccerchallenge.api

import android.content.Context
import com.lucas.soccerchallenge.api.ConnectionUtil.isConnectionOn
import com.lucas.soccerchallenge.api.ConnectionUtil.isInternetAvailable
import com.lucas.soccerchallenge.base.networking.exception.NoInternetException
import com.lucas.soccerchallenge.base.networking.exception.NoNetworkException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoConnectionInterceptor @Inject constructor(
    private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!isConnectionOn(context)) {
            throw NoNetworkException()
        } else if (!isInternetAvailable()) {
            throw NoInternetException()
        } else {
            chain.proceed(chain.request())
        }
    }

}