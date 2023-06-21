package com.soccerchallenge.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.soccerchallenge.data.database.dao.MatchDao
import com.soccerchallenge.data.database.entity.MatchEntity
import com.soccerchallenge.data.database.util.DateConverter
import com.soccerchallenge.data.database.util.MatchTypeConverter

@Database(
        entities = [MatchEntity::class],
        version = SoccerDatabase.VERSION,
        exportSchema = false
)
@TypeConverters(value = [DateConverter::class, MatchTypeConverter::class])
abstract class SoccerDatabase : RoomDatabase() {

    companion object {
        const val VERSION = 1
        const val DATABASE_NAME = "soccer-db"
    }

    abstract fun matchDao(): MatchDao

}
