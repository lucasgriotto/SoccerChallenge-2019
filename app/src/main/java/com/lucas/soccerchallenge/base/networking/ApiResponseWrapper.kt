package com.lucas.soccerchallenge.base.networking

import android.util.Log
import retrofit2.Response

abstract class ApiResponseWrapper<R>(
    val onResult: (Resource<Nothing>) -> Unit
) {

    abstract suspend fun getCall(): Response<R>

    abstract fun onSuccess(result: R)

    open fun onHttpException(r: Response<R>) {
        onResult(Resource.Error(r.errorBody().toString()))
    }

    suspend fun getData() {
        onResult.invoke(Resource.Loading())
        try {
            val response = getCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    onSuccess(it)
                } ?: onResult(Resource.Error("Empty body"))
            } else
                onHttpException(response)
        } catch (e: Exception) {
            Log.e("Networking", e.message ?: "")
            onResult(Resource.Error(e.message ?: "Error"))
        }
    }
}