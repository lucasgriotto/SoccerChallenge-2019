package com.lucas.soccerchallenge.utils.extension

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

@SuppressLint("CheckResult")
fun ImageView.loadImageUrl(url: String?, @DrawableRes placeholder: Int? = null) {
    Glide.with(this)
            .load(url)
            .also { builder ->
                placeholder?.let { builder.placeholder(it) }
            }
            .into(this)
}