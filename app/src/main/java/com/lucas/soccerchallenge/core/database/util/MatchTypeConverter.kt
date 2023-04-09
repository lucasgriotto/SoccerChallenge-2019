package com.lucas.soccerchallenge.core.database.util

import androidx.room.TypeConverter
import com.lucas.soccerchallenge.core.model.MatchType

class MatchTypeConverter {

    @TypeConverter
    fun matchTypeToString(matchType: MatchType?) = matchType?.name

    @TypeConverter
    fun stringToMatchType(value: String): MatchType = MatchType.valueOf(value)

}