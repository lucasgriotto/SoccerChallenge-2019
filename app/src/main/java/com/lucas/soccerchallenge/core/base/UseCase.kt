package com.lucas.soccerchallenge.core.base

import com.lucas.soccerchallenge.core.networking.ErrorFactory
import com.lucas.soccerchallenge.core.networking.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class UseCase<R, Params> {

    private val result = MutableStateFlow<Resource<R>>(Resource.Initialize())

    private val handler = CoroutineExceptionHandler { _, error ->
        Timber.e("Caught $error")
        result.value = Resource.Error(ErrorFactory.getError(error))
    }

    fun execute(scope: CoroutineScope, params: Params) {
        result.value = Resource.Loading()
        scope.launch(handler) {
            val results = doWork(params)
            result.value = Resource.Success(results)
        }
    }

    open fun result(): StateFlow<Resource<R>> = result

    abstract suspend fun doWork(params: Params): R
}
 