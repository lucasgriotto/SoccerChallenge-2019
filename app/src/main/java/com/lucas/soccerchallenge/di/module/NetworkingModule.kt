package com.lucas.soccerchallenge.di.module

import com.google.gson.GsonBuilder
import com.lucas.soccerchallenge.BuildConfig
import com.lucas.soccerchallenge.api.NoConnectionInterceptor
import com.lucas.soccerchallenge.api.SoccerService
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

    companion object {
        const val BASE_URL = "https://storage.googleapis.com/cdn-og-test-api/"

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
        loggingInterceptor: HttpLoggingInterceptor,
        noConnectionInterceptor: NoConnectionInterceptor
    ): Retrofit {

        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(noConnectionInterceptor)


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
}