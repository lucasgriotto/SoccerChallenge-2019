package com.soccerchallenge.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.soccerchallenge.data.datasource.LocalSoccerDataSource
import com.soccerchallenge.data.datasource.LocalSoccerDataSourceImpl
import com.soccerchallenge.data.datasource.RemoteSoccerDataSource
import com.soccerchallenge.data.datasource.RemoteSoccerDataSourceImpl
import com.soccerchallenge.data.repository.SoccerRepositoryImpl
import com.soccerchallenge.data.repository.UserPreferencesRepositoryImpl
import com.soccerchallenge.domain.repository.SoccerRepository
import com.soccerchallenge.domain.repository.UserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Suppress("UNUSED")
abstract class DataModule {

    @Binds
    abstract fun bindSoccerRepository(repository: SoccerRepositoryImpl): SoccerRepository

    @Binds
    abstract fun bindLocalDataSource(localSoccerDataSource: LocalSoccerDataSourceImpl): LocalSoccerDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteSoccerDataSource: RemoteSoccerDataSourceImpl): RemoteSoccerDataSource

    @Binds
    abstract fun bindUserPreferencesRepository(repository: UserPreferencesRepositoryImpl): UserPreferencesRepository

    companion object {

        private const val USER_PREFERENCES_NAME = "user_preferences"

        @Singleton
        @Provides
        fun providePreferencesDataStore(appContext: Context): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create(
                    corruptionHandler = ReplaceFileCorruptionHandler(
                            produceNewData = { emptyPreferences() }
                    ),
                    produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES_NAME) }
            )
        }

    }

}
