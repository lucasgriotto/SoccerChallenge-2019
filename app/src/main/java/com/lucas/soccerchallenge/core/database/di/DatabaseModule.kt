package com.lucas.soccerchallenge.core.database.di

import android.content.Context
import androidx.room.Room
import com.lucas.soccerchallenge.core.database.SoccerDatabase
import com.lucas.soccerchallenge.core.database.dao.MatchDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Suppress("UNUSED")
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): SoccerDatabase {
        return Room
            .databaseBuilder(context, SoccerDatabase::class.java, SoccerDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMatchDao(appDatabase: SoccerDatabase): MatchDao {
        return appDatabase.matchDao()
    }

}
