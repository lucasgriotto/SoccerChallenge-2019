package com.lucas.soccerchallenge.features.fixture.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lucas.soccerchallenge.data.Match

abstract class MatchFilterViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(previous: Match?, current: Match)

}
