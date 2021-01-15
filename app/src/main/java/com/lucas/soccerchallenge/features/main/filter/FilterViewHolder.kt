package com.lucas.soccerchallenge.features.main.filter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.data.model.Competition
import com.lucas.soccerchallenge.databinding.ItemFilterBinding
import com.lucas.soccerchallenge.databinding.ItemMatchFixtureBinding
import com.lucas.soccerchallenge.features.fixture.adapter.MatchFixtureViewHolder
import kotlinx.android.synthetic.main.item_filter.view.*

class FilterViewHolder constructor(
    private val binding: ItemFilterBinding,
    private val onFilterSelected: (Competition, Boolean) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bindTo(selectStatus: Boolean, competition: Competition) {
        binding.apply {
            txtTitle.text = competition.name

            btnSelectUnselect.isSelected = selectStatus

            root.setOnClickListener {
                btnSelectUnselect.isSelected = !btnSelectUnselect.isSelected
                onFilterSelected(competition, btnSelectUnselect.isSelected)
            }
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            onFilterSelected: (Competition, Boolean) -> Unit
        ): FilterViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemFilterBinding.inflate(inflater, parent, false)
            return FilterViewHolder(binding, onFilterSelected)
        }
    }
}
