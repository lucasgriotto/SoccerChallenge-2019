package com.lucas.soccerchallenge.core.data.di

import com.lucas.soccerchallenge.core.data.repository.SoccerRepository
import com.lucas.soccerchallenge.core.data.repository.SoccerRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
@Suppress("UNUSED")
abstract class DataModule {

    @Binds
    abstract fun bindSoccerRepository(repository: SoccerRepositoryImpl): SoccerRepository

}
