package com.lucas.soccerchallenge.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lucas.soccerchallenge.core.database.dao.MatchDao
import com.lucas.soccerchallenge.core.database.entity.MatchEntity
import com.lucas.soccerchallenge.core.database.util.DateConverter
import com.lucas.soccerchallenge.core.database.util.MatchTypeConverter

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
