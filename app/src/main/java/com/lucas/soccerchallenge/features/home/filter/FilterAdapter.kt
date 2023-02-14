package com.lucas.soccerchallenge.features.home.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucas.soccerchallenge.data.Competition
import com.lucas.soccerchallenge.databinding.ItemFilterBinding
import javax.inject.Inject

class FilterAdapter @Inject constructor() : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {

    private var selectedCompetitions = Filters.defaultSelectedCompetition.toHashSet()

    var selectedCompetitionsBackUp = selectedCompetitions.toSet()
        private set

    private val competition = Filters.competitionFilter

    private var onFilterSelected: (Competition, Boolean, Int) -> Unit = { comp, isSelected, position ->
        when {
            comp.id == Filters.ALL_FILTER_ID -> {
                selectedCompetitions.clear()
                selectedCompetitions.add(comp)
                notifyDataSetChanged()
            }
            isSelected -> {
                selectedCompetitions.remove(competition[0])
                notifyItemChanged(0)
                selectedCompetitions.add(comp)
                notifyItemChanged(position)
            }
            else -> selectedCompetitions.remove(comp)
        }
    }

    fun backUpSelectedCompetitions() {
        selectedCompetitionsBackUp = selectedCompetitions.toSet()
    }

    fun restoreSelectedCompetitions() {
        selectedCompetitions = selectedCompetitionsBackUp.toHashSet()
        notifyDataSetChanged()
    }

    fun applyFilters() {
        if (selectedCompetitions.size == competition.size - 1) {
            selectedCompetitions.clear()
            selectedCompetitions.add(Filters.allFilterCompetition)
        } else if (selectedCompetitions.isEmpty()) {
            selectedCompetitions.add(Filters.allFilterCompetition)
        }
        backUpSelectedCompetitions()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilterBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, onFilterSelected)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            selectedCompetitions.contains(competition[position]),
            competition[position],
            position
        )
    }

    override fun getItemCount(): Int {
        return competition.size
    }

    inner class ViewHolder(
        private val binding: ItemFilterBinding,
        private val onFilterSelected: (Competition, Boolean, Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(selectStatus: Boolean, competition: Competition, position: Int) {
            binding.apply {
                txtTitle.text = competition.name
                btnSelectUnselect.isSelected = selectStatus
                root.setOnClickListener {
                    btnSelectUnselect.isSelected = !btnSelectUnselect.isSelected
                    onFilterSelected(competition, btnSelectUnselect.isSelected, position)
                }
            }
        }
    }

}
