package com.soccerchallenge.data.database.util

import androidx.room.TypeConverter
import com.soccerchallenge.domain.model.MatchType

class MatchTypeConverter {

    @TypeConverter
    fun matchTypeToString(matchType: MatchType?) = matchType?.name

    @TypeConverter
    fun stringToMatchType(value: String): MatchType = MatchType.valueOf(value)

}