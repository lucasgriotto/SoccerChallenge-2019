package com.lucas.soccerchallenge.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Score(
    val home: Int,
    val away: Int,
    val winner: String?
) : Parcelable