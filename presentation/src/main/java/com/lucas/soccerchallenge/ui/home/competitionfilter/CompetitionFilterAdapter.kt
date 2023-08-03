package com.lucas.soccerchallenge.ui.home.competitionfilter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucas.soccerchallenge.databinding.ItemFilterBinding
import com.lucas.soccerchallenge.ui.home.competitionfilter.model.CompetitionFilterDisplayModel
import com.soccerchallenge.data.util.CompetitionFilters
import com.soccerchallenge.data.util.CompetitionFilters.ALL_FILTER_INDEX
import javax.inject.Inject

class CompetitionFilterAdapter @Inject constructor() :
    RecyclerView.Adapter<CompetitionFilterAdapter.ViewHolder>() {

    private val _filters = mutableListOf<CompetitionFilterDisplayModel>()
    val filters: List<CompetitionFilterDisplayModel>
        get() {
            // Copy is created because if we change filter options and we don't press apply we need to restore previous
            // state of the list
            return _filters.map { it.copy() }
        }

    private var onFilterSelected: (CompetitionFilterDisplayModel) -> Unit = { filter ->
        when {
            filter.id == CompetitionFilters.ALL_FILTER_ID -> {
                // If all is selected, we have to unselect the rest. That logic is handled here
                _filters.forEachIndexed { index, filterCompetitionDisplayModel ->
                    if (index != ALL_FILTER_INDEX && filterCompetitionDisplayModel.isSelected) {
                        filterCompetitionDisplayModel.isSelected = false
                        notifyItemChanged(index)
                    } else if (index == ALL_FILTER_INDEX && !filterCompetitionDisplayModel.isSelected) {
                        filterCompetitionDisplayModel.isSelected = true
                        notifyItemChanged(index)
                    }
                }
            }

            filter.isSelected -> {
                if (_filters[ALL_FILTER_INDEX].isSelected) {
                    _filters[ALL_FILTER_INDEX].isSelected = false
                    notifyItemChanged(ALL_FILTER_INDEX)
                }
            }
        }
    }

    fun setFilters(newFilters: List<CompetitionFilterDisplayModel>) {
        if (_filters.isEmpty()) {
            // Copy is created because if we change filter options and we don't press apply we need to restore previous
            // state of the list
            _filters.addAll(newFilters.map { it.copy() })
        } else {
            _filters.forEachIndexed { index, filterCompetitionDisplayModel ->
                if (newFilters[index].isSelected != filterCompetitionDisplayModel.isSelected) {
                    filterCompetitionDisplayModel.isSelected = newFilters[index].isSelected
                    notifyItemChanged(index)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilterBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, onFilterSelected)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        _filters.getOrNull(position)?.let { filter ->
            holder.bind(filter)
        }
    }

    override fun getItemCount(): Int {
        return _filters.size
    }

    inner class ViewHolder(
        private val binding: ItemFilterBinding,
        private val onFilterSelected: (CompetitionFilterDisplayModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(filter: CompetitionFilterDisplayModel) {
            binding.apply {
                title.text = filter.name
                selectUnselectBtn.isSelected = filter.isSelected
                root.setOnClickListener {
                    selectUnselectBtn.isSelected = !selectUnselectBtn.isSelected
                    filter.isSelected = selectUnselectBtn.isSelected
                    onFilterSelected(filter)
                }
            }
        }
    }

}
