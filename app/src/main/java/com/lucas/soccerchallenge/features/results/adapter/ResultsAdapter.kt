package com.lucas.soccerchallenge.features.results.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucas.soccerchallenge.data.model.Match
import javax.inject.Inject

class ResultsAdapter @Inject
constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list: List<Match> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MatchResultViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MatchResultViewHolder).bindTo(
            if (position == 0) null else list[position - 1],
            list[position]
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }
}