package com.lucas.soccerchallenge.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.lucas.soccerchallenge.base.networking.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
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
        result.value = Resource.Error(e)
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