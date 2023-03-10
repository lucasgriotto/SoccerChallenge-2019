package com.lucas.soccerchallenge.di.module

import android.app.Application
import android.content.Context
import com.lucas.soccerchallenge.di.qualifier.DefaultDispatcher
import com.lucas.soccerchallenge.di.qualifier.IODispatcher
import com.lucas.soccerchallenge.repository.SoccerRepository
import com.lucas.soccerchallenge.repository.SoccerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@Suppress("UNUSED")
abstract class ApplicationModule {

    @Binds
    abstract fun bindContext(application: Application): Context

    @Binds
    abstract fun bindSoccerRepository(repository: SoccerRepositoryImpl): SoccerRepository


    companion object {

        @Provides
        @DefaultDispatcher
        fun defaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

        @Provides
        @IODispatcher
        fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO
    }

}
