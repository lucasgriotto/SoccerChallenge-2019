package com.lucas.soccerchallenge.di

import android.app.Application
import com.lucas.soccerchallenge.SoccerChallengeApp
import com.lucas.soccerchallenge.core.data.di.DataModule
import com.lucas.soccerchallenge.core.network.di.NetworkModule
import com.lucas.soccerchallenge.di.module.ActivityModule
import com.lucas.soccerchallenge.di.module.ApplicationModule
import com.lucas.soccerchallenge.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        ActivityModule::class,
        ViewModelModule::class,
        NetworkModule::class,
        DataModule::class]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: SoccerChallengeApp)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun applicationBind(application: Application): Builder
    }
}