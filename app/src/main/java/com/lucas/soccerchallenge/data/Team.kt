package com.lucas.soccerchallenge.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Team(
    val name: String
) : Parcelable