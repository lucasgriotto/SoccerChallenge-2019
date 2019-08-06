package com.lucas.soccerchallenge.features.filter.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucas.soccerchallenge.data.model.Competition
import javax.inject.Inject

class FilterAdapter @Inject
constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val competition = listOf(
        Competition(8, "Premier League"),
        Competition(2, "Carabao Cup"),
        Competition(6, "UEFA Europa League"),
        Competition(1, "FA Cup")
    )

    lateinit var onFilterListener: () -> Unit

    private var onFilterSelected: (Competition, Boolean) -> Unit = { comp, isSelected ->
        if(isSelected)
            selectedCompetitions.add(comp)
        else
            selectedCompetitions.remove(comp)
        onFilterListener()
    }

    var selectedCompetitions = HashSet<Competition>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FilterViewHolder.create(parent, onFilterSelected)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FilterViewHolder).bindTo(
            selectedCompetitions.contains(competition[position]),
            competition[position]
        )
    }

    override fun getItemCount(): Int {
        return competition.size
    }

    fun onSelectAll() {
        selectedCompetitions.clear()
        notifyDataSetChanged()
    }
}