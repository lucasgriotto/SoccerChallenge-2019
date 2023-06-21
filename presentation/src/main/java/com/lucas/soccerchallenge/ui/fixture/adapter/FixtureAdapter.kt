package com.lucas.soccerchallenge.ui.fixture.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.databinding.ItemMatchFixtureBinding
import com.lucas.soccerchallenge.databinding.ItemMatchHeaderBinding
import com.lucas.soccerchallenge.ui.home.match.MatchHeaderViewHolder
import com.lucas.soccerchallenge.ui.home.matchfilter.MatchDiffCallBack
import com.lucas.soccerchallenge.ui.home.matchfilter.model.MatchHeaderDisplayModel
import com.lucas.soccerchallenge.ui.home.matchfilter.model.MatchItemDisplayModel
import com.lucas.soccerchallenge.utils.DateUtils
import com.lucas.soccerchallenge.utils.extension.color
import com.soccerchallenge.data.di.qualifier.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FixtureAdapter @Inject constructor(
        @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var onMatchClick: (FixtureDisplayModel) -> Unit

    private val matchesWithHeaders = mutableListOf<MatchItemDisplayModel>()

    suspend fun setMatches(newList: List<MatchItemDisplayModel>) {
        val diffCallback = MatchDiffCallBack(matchesWithHeaders, newList)
        val diffResult = withContext(dispatcher) { DiffUtil.calculateDiff(diffCallback) }
        matchesWithHeaders.clear()
        matchesWithHeaders.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_match_fixture -> {
                val binding = ItemMatchFixtureBinding.inflate(inflater, parent, false)
                MatchViewHolder(binding)
            }

            else -> {
                val binding = ItemMatchHeaderBinding.inflate(inflater, parent, false)
                MatchHeaderViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return matchesWithHeaders.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (matchesWithHeaders.getOrNull(position) is FixtureDisplayModel) {
            R.layout.item_match_fixture
        } else {
            R.layout.item_match_header
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        matchesWithHeaders.getOrNull(position)?.let { item ->
            when {
                item is FixtureDisplayModel && holder is MatchViewHolder -> {
                    holder.bind(item)
                }

                item is MatchHeaderDisplayModel && holder is MatchHeaderViewHolder -> {
                    holder.bind(item)
                }
            }
        }
    }

    inner class MatchViewHolder constructor(
            private val binding: ItemMatchFixtureBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(match: FixtureDisplayModel) {
            val context = itemView.context
            binding.fixture.apply {
                root.setOnClickListener {
                    onMatchClick(match)
                }
                competition.text = match.competitionName
                venue.text = match.venueName.plus(" | ")
                date.text = DateUtils.getUIFormattedDate(match.date)

                homeTeam.text = match.teamHomeName
                awayTeam.text = match.teamAwayName

                dayNum.text = DateUtils.getMonthDayNumber(match.date)
                dayName.text = DateUtils.getWeekDayNameShort(match.date)

                postponed.isVisible = match.isPostponed
                date.setTextColor(context.color(match.matchDateColor))
            }
        }
    }

}
