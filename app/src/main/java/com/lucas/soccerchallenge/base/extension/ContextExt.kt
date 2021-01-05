package com.lucas.soccerchallenge.base

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun Context.color(@ColorRes color: Int) = ContextCompat.getColor(this, color)
