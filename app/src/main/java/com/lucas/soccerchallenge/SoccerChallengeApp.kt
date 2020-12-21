package com.lucas.soccerchallenge

import android.app.Application
import com.lucas.soccerchallenge.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class SoccerChallengeApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent
            .builder()
            .applicationBind(this)
            .build()
            .inject(this)
    }
}