package com.lucas.soccerchallenge.base.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

abstract class LiveDataUseCase<in Params, Type> : UseCase<Params>() {

    protected val result = MediatorLiveData<Type>()

    open fun observe(): LiveData<Type> = result
}