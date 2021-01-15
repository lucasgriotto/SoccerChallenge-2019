package com.lucas.soccerchallenge.features.main.filter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucas.soccerchallenge.data.model.Competition
import javax.inject.Inject

class FilterAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val competition = Filters.competitionFilter

    private var onFilterSelected: (Competition, Boolean) -> Unit = { comp, isSelected ->
        when {
            comp.id == Filters.ALL_FILTER_ID -> {
                selectedCompetitions.clear()
                selectedCompetitions.add(comp)
                notifyDataSetChanged()
            }
            isSelected -> {
                selectedCompetitions.remove(competition[0])
                selectedCompetitions.add(comp)
                notifyDataSetChanged()
            }
            else -> selectedCompetitions.remove(comp)
        }
    }

    var selectedCompetitions = hashSetOf(Filters.allFilterCompetition)

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

    fun ifFiltersEmptySelectAll() {
        if (selectedCompetitions.isEmpty()) {
            selectedCompetitions.add(Filters.allFilterCompetition)
            notifyDataSetChanged()
        }
    }

    fun setBackedUpFilters(filters: HashSet<Competition>) {
        selectedCompetitions = filters
        notifyDataSetChanged()
    }
}