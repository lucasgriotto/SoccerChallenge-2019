package com.lucas.soccerchallenge.features.fixture.adapter

import androidx.recyclerview.widget.DiffUtil
import com.lucas.soccerchallenge.data.Match

class MatchDiffCallBack(
    private val oldList: List<Match>,
    private val newList: List<Match>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }

}
