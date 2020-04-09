package com.lucas.soccerchallenge.di.module

import android.content.Context
import com.google.gson.GsonBuilder
import com.lucas.soccerchallenge.BuildConfig
import com.lucas.soccerchallenge.BuildConfig.BASE_URL
import com.lucas.soccerchallenge.api.SoccerService
import com.lucas.soccerchallenge.base.networking.CheckConnectivityInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkingModule {

    companion object{
        const val DATE_FORMAT_SERVER = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    }

    @Provides
    @Singleton
    fun provideSoccerService(retrofit: Retrofit): SoccerService {
        return retrofit.create(SoccerService::class.java)
    }

    @Provides
    @Singleton
    fun providesRetrofitClient(
        checkConnectivityInterceptor: CheckConnectivityInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): Retrofit {

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(loggingInterceptor)
            .addInterceptor(checkConnectivityInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)

        val gsonDate = GsonBuilder()
            .setDateFormat(DATE_FORMAT_SERVER)
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonDate))
            .client(httpClient.build())
            .build()
    }

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(
                if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else
                    HttpLoggingInterceptor.Level.NONE
            )
    }

    @Provides
    @Singleton
    fun providesConnectivityInterceptor(context: Context): CheckConnectivityInterceptor {
        return CheckConnectivityInterceptor(context)
    }
}