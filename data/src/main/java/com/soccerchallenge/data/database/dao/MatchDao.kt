package com.soccerchallenge.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.soccerchallenge.data.database.entity.MatchEntity
import com.soccerchallenge.domain.model.MatchType

@Dao
interface MatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatches(matchList: List<MatchEntity>)

    @Query("DELETE FROM matches WHERE type = :type")
    suspend fun deleteMatches(type: MatchType)

    @Query("SELECT * FROM matches WHERE type = :type ORDER BY date ASC")
    suspend fun getMatches(type: MatchType): List<MatchEntity>

    @Query("SELECT * FROM matches WHERE id = :matchId")
    suspend fun getMatch(matchId: Int): MatchEntity

}
