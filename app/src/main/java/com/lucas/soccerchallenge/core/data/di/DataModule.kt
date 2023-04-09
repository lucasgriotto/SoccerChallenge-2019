package com.lucas.soccerchallenge.core.data.di

import com.lucas.soccerchallenge.core.data.datasource.LocalSoccerDataSource
import com.lucas.soccerchallenge.core.data.datasource.LocalSoccerDataSourceImpl
import com.lucas.soccerchallenge.core.data.datasource.RemoteSoccerDataSource
import com.lucas.soccerchallenge.core.data.datasource.RemoteSoccerDataSourceImpl
import com.lucas.soccerchallenge.core.data.repository.SoccerRepository
import com.lucas.soccerchallenge.core.data.repository.SoccerRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
@Suppress("UNUSED")
abstract class DataModule {

    @Binds
    abstract fun bindSoccerRepository(repository: SoccerRepositoryImpl): SoccerRepository

    @Binds
    abstract fun bindLocalDataSource(localSoccerDataSource: LocalSoccerDataSourceImpl): LocalSoccerDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteSoccerDataSource: RemoteSoccerDataSourceImpl): RemoteSoccerDataSource

}
