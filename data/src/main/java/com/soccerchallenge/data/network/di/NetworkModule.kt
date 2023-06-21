package com.soccerchallenge.data.network.di

import com.google.gson.GsonBuilder
import com.soccerchallenge.data.BuildConfig
import com.soccerchallenge.data.network.service.SoccerService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@Suppress("UNUSED")
class NetworkModule {

    @Provides
    @Singleton
    fun provideSoccerService(retrofit: Retrofit): SoccerService {
        return retrofit.create(SoccerService::class.java)
    }

    @Provides
    @Singleton
    fun providesRetrofitClient(
            loggingInterceptor: HttpLoggingInterceptor
    ): Retrofit {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)

        val gsonDate = GsonBuilder()
                .setDateFormat(DATE_FORMAT_SERVER)
                .create()

        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
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

    companion object {
        private const val DATE_FORMAT_SERVER = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    }

}