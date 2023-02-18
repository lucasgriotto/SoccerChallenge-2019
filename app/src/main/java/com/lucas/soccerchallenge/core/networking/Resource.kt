package com.lucas.soccerchallenge.core.networking

sealed class Resource<out T> {
    class Initialize<T> : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Loading<out T>(val partialData: T? = null) : Resource<T>()
    data class Error<out T>(val error: AppError, val data: T? = null) : Resource<T>()
}