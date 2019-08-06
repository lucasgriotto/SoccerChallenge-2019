package com.lucas.soccerchallenge.features.filter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.data.model.Competition
import kotlinx.android.synthetic.main.item_filter.view.*

class FilterViewHolder private constructor(
    itemView: View,
    private val onFilterSelected: (Competition, Boolean) -> Unit
) :
    RecyclerView.ViewHolder(itemView) {

    fun bindTo(selectStatus: Boolean, competition: Competition) {
        itemView.apply {
            txt_title.text = competition.name

            btn_select_unselect.isSelected = selectStatus

            setOnClickListener {
                btn_select_unselect.isSelected = !btn_select_unselect.isSelected
                onFilterSelected(competition, btn_select_unselect.isSelected)
            }
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            onFilterSelected: (Competition, Boolean) -> Unit
        ): FilterViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_filter, parent, false)
            return FilterViewHolder(view, onFilterSelected)
        }
    }
}
