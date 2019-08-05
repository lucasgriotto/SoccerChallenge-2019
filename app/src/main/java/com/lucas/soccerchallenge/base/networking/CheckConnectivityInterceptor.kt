package com.lucas.soccerchallenge.base.networking

import android.content.Context
import android.net.ConnectivityManager
import com.lucas.soccerchallenge.R
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class CheckConnectivityInterceptor(var context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val netInfo =
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .activeNetworkInfo
        if (!(netInfo != null && netInfo.isConnected)) {
            throw NoConnectivityException()
        }
        return chain.proceed(chain.request())
    }

    inner class NoConnectivityException : IOException() {

        override val message: String?
            get() = context.resources.getString(R.string.error_connection)
    }
}