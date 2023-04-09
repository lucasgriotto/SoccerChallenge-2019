package com.lucas.soccerchallenge.ui.base

import com.lucas.soccerchallenge.utils.errorfactory.ErrorFactory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class LoadableUseCase<R, Params> {

    private val _result = MutableStateFlow<Resource<R>>(Resource.Initialize())

    val result: Flow<Resource<R>> = _result

    private val handler = CoroutineExceptionHandler { _, error ->
        Timber.e("Caught $error")
        _result.value = Resource.Error(ErrorFactory.getError(error))
    }

    fun execute(scope: CoroutineScope, params: Params) {
        _result.value = Resource.Loading()
        scope.launch(handler) {
            val results = doWork(params)
            _result.value = Resource.Success(results)
        }
    }

    abstract suspend fun doWork(params: Params): R

}