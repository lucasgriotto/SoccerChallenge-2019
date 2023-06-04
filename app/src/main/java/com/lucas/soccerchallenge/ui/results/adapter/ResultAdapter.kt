package com.lucas.soccerchallenge.ui.results.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.databinding.ItemMatchHeaderBinding
import com.lucas.soccerchallenge.databinding.ItemMatchResultBinding
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

class ResultAdapter @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var onMatchClick: (ResultDisplayModel) -> Unit

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
            R.layout.item_match_result -> {
                val binding = ItemMatchResultBinding.inflate(inflater, parent, false)
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
        return if (matchesWithHeaders.getOrNull(position) is ResultDisplayModel) {
            R.layout.item_match_result
        } else {
            R.layout.item_match_header
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        matchesWithHeaders.getOrNull(position)?.let { item ->
            when {
                item is ResultDisplayModel && holder is MatchViewHolder -> {
                    holder.bind(item)
                }
                item is MatchHeaderDisplayModel && holder is MatchHeaderViewHolder -> {
                    holder.bind(item)
                }
            }
        }
    }

    inner class MatchViewHolder constructor(
        private val binding: ItemMatchResultBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(match: ResultDisplayModel) {
            val context = itemView.context
            binding.result.apply {
                root.setOnClickListener {
                    onMatchClick(match)
                }
                competition.text = match.competitionName
                val stringDate = DateUtils.getUIFormattedDate(match.date)
                venueDate.text = match.venueName.plus(" | ").plus(stringDate)

                homeTeam.text = match.teamHomeName
                awayTeam.text = match.teamAwayName

                homeTeamScore.text = match.teamHomeScore
                homeTeamScore.setTextColor(context.color(match.scoreHomeColor))

                awayTeamScore.text = match.teamAwayScore
                awayTeamScore.setTextColor(context.color(match.scoreAwayColor))
            }
        }
    }

}
