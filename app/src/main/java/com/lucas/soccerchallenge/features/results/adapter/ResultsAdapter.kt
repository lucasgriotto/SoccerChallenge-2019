package com.lucas.soccerchallenge.features.results.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucas.soccerchallenge.data.model.Competition
import com.lucas.soccerchallenge.data.model.Match
import com.lucas.soccerchallenge.features.filter.MatchFilter
import javax.inject.Inject

class ResultsAdapter @Inject
constructor(private val matchFilter: MatchFilter) : RecyclerView.Adapter<MatchResultViewHolder>() {

    fun setFilter(filters: HashSet<Competition>?) {
        matchFilter.filters = filters
        notifyDataSetChanged()
    }

    fun setList(list: List<Match>) {
        matchFilter.setList(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchResultViewHolder {
        return MatchResultViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MatchResultViewHolder, position: Int) {
        holder.bindTo(
            matchFilter.filteredList.getOrNull(position - 1),
            matchFilter.filteredList.getOrNull(position)
        )
    }

    override fun getItemCount(): Int {
        return matchFilter.filteredList.size
    }
}