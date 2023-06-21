package com.lucas.soccerchallenge.ui.home.matchfilter

import androidx.recyclerview.widget.DiffUtil
import com.lucas.soccerchallenge.ui.fixture.adapter.FixtureDisplayModel
import com.lucas.soccerchallenge.ui.home.matchfilter.model.MatchHeaderDisplayModel
import com.lucas.soccerchallenge.ui.home.matchfilter.model.MatchItemDisplayModel
import com.lucas.soccerchallenge.ui.results.adapter.ResultDisplayModel

class MatchDiffCallBack(
        private val oldList: List<MatchItemDisplayModel>,
        private val newList: List<MatchItemDisplayModel>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem is FixtureDisplayModel &&
                newItem is FixtureDisplayModel &&
                oldItem.id == newItem.id ||
                oldItem is ResultDisplayModel &&
                newItem is ResultDisplayModel &&
                oldItem.id == newItem.id ||
                oldItem is MatchHeaderDisplayModel &&
                newItem is MatchHeaderDisplayModel &&
                oldItem.monthYear == newItem.monthYear
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }

}