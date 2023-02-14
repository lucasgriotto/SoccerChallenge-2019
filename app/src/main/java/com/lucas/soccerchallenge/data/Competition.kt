package com.lucas.soccerchallenge.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Competition(
    val id: Int,
    val name: String
) : Parcelable