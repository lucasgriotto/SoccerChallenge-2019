package com.lucas.soccerchallenge.base.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class UseCase<in Params> {

    private val job = Job()

    fun execute(params: Params) {
        CoroutineScope(Dispatchers.Main + job).launch {
            getData(params)
        }
    }

    abstract suspend fun getData(params: Params)

    fun cancel() {
        job.cancel()
    }
}