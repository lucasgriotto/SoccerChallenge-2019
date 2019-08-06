package com.lucas.soccerchallenge.features.fixture.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucas.soccerchallenge.data.model.Competition
import com.lucas.soccerchallenge.data.model.Match
import javax.inject.Inject

class FixtureAdapter @Inject
constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var fullList: List<Match>

    var filters: HashSet<Competition>? = null
        set(value) {
            field = value
            list = fullList
        }

    private var list: List<Match> = emptyList()
        set(value) {
            field = getFilteredList(value)
            notifyDataSetChanged()
        }

    fun setListFirst(list: List<Match>) {
        fullList = list
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MatchFixtureViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MatchFixtureViewHolder).bindTo(
            if (position == 0) null else list[position - 1],
            list[position]
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private fun getFilteredList(list: List<Match>): List<Match> {
        return filters?.let { fil ->
            list.filter { fil.contains(it.competition) }
        } ?: list
    }
}