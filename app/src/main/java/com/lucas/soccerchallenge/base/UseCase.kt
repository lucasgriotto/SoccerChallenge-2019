package com.lucas.soccerchallenge.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.lucas.soccerchallenge.base.networking.AppError
import com.lucas.soccerchallenge.base.networking.ErrorEntityFactory
import com.lucas.soccerchallenge.base.networking.Resource
import kotlinx.coroutines.*
import timber.log.Timber

abstract class UseCase<R, Params> {

    private var job = Job()

    private val result = MediatorLiveData<Resource<R>>()

    private fun getJob(): Job {
        if (!job.isActive)
            job = Job()
        return job
    }

    private val handler = CoroutineExceptionHandler { _, e ->
        Timber.e("Caught $e")
        result.value = Resource.Error(e as? AppError ?: ErrorEntityFactory.getError(e))
    }

    fun execute(params: Params) {
        result.value = Resource.Loading()
        CoroutineScope(Dispatchers.Main + getJob()).launch(handler) {
            result.value = Resource.Success(getData(params))
        }
    }

    open fun observe(): LiveData<Resource<R>> = result

    abstract suspend fun getData(e: Params): R

    fun cancel() = job.cancel()
}