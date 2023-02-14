package com.lucas.soccerchallenge.di.module

import android.app.Application
import android.content.Context
import com.lucas.soccerchallenge.repository.SoccerRepository
import com.lucas.soccerchallenge.repository.SoccerRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
@Suppress("UNUSED")
abstract class ApplicationModule {

    @Binds
    abstract fun bindContext(application: Application): Context

    @Binds
    abstract fun bindSoccerRepository(repository: SoccerRepositoryImpl): SoccerRepository

}
