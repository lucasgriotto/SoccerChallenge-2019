package com.lucas.soccerchallenge.core.base

import com.lucas.soccerchallenge.core.networking.ErrorFactory
import com.lucas.soccerchallenge.core.networking.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class LoadableUseCase<R, Params> {

    private val _result = MutableStateFlow<Resource<R>>(Resource.Initialize())

    val result: Flow<Resource<R>> = _result

    fun execute(scope: CoroutineScope, params: Params) {
        _result.value = Resource.Loading()
        scope.launch {
            getFlow(params)
                .catch {
                    Timber.e("Caught $it")
                    _result.value = Resource.Error(ErrorFactory.getError(it))
                }
                .collect {
                    _result.value = Resource.Success(it)
                }
        }
    }

    abstract fun getFlow(params: Params): Flow<R>

}