package com.lucas.soccerchallenge.features.fixture.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucas.soccerchallenge.data.model.Competition
import com.lucas.soccerchallenge.data.model.Match
import com.lucas.soccerchallenge.features.filter.MatchFilter
import java.util.*
import javax.inject.Inject

class FixtureAdapter @Inject
constructor(private val matchFilter: MatchFilter) : RecyclerView.Adapter<MatchFixtureViewHolder>() {

    fun setFilter(filters: HashSet<Competition>?) {
        matchFilter.filters = filters
        notifyDataSetChanged()
    }

    fun setList(list: List<Match>) {
        matchFilter.setList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchFixtureViewHolder {
        return MatchFixtureViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MatchFixtureViewHolder, position: Int) {
        holder.bindTo(
            if (position == 0) null else matchFilter.filteredList[position - 1],
            matchFilter.filteredList[position]
        )
    }

    override fun getItemCount(): Int {
        return matchFilter.filteredList.size
    }
}