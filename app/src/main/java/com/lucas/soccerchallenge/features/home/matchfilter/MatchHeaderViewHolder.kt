package com.lucas.soccerchallenge.features.home.matchfilter

import androidx.recyclerview.widget.RecyclerView
import com.lucas.soccerchallenge.databinding.ItemMatchHeaderBinding
import com.lucas.soccerchallenge.features.home.matchfilter.model.MatchHeaderDisplayModel

class MatchHeaderViewHolder constructor(
    private val binding: ItemMatchHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(header: MatchHeaderDisplayModel) {
        binding.apply {
            monthYear.text = header.monthYear
        }
    }

}
