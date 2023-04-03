package com.lucas.soccerchallenge.utils.extension

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.color(@ColorRes color: Int) = ContextCompat.getColor(this, color)
