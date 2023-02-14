package com.lucas.soccerchallenge.features.results.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.core.extension.color
import com.lucas.soccerchallenge.data.Match
import com.lucas.soccerchallenge.databinding.ItemMatchResultBinding
import com.lucas.soccerchallenge.features.fixture.adapter.MatchFilterAdapter
import com.lucas.soccerchallenge.features.fixture.adapter.MatchFilterViewHolder
import com.lucas.soccerchallenge.features.home.filter.MatchFilter
import com.lucas.soccerchallenge.utils.DateUtils
import javax.inject.Inject

class ResultAdapter @Inject constructor(
    matchFilter: MatchFilter
) : MatchFilterAdapter<ResultAdapter.ViewHolder>(matchFilter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMatchResultBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder constructor(
        private val binding: ItemMatchResultBinding
    ) : MatchFilterViewHolder(binding.root) {

        override fun bind(previous: Match?, current: Match) {
            val context = itemView.context

            binding.header.apply {
                if (previous == null || !DateUtils.isSameMonthYear(previous.date, current.date)) {
                    txtMonthYear.text = DateUtils.getMonthYear(current.date)
                    root.isVisible = true
                } else {
                    root.isVisible = false
                }
            }

            binding.result.apply {
                root.setOnClickListener {
                    onMatchClick(current)
                }
                txtCompetition.text = current.competition.name
                txtVenueDate.text = current.venueName
                    .plus(" | ")
                    .plus(DateUtils.getUIFormattedDate(current.date))

                txtTeamHome.text = current.homeTeam.name
                txtTeamAway.text = current.awayTeam.name

                txtScoreHome.text = current.score?.home.toString()
                txtScoreAway.text = current.score?.away.toString()

                when {
                    current.isHomeWinner -> {
                        txtScoreHome.setTextColor(context.color(R.color.blue))
                        txtScoreAway.setTextColor(context.color(R.color.darkBlue))
                    }
                    current.isAwayWinner -> {
                        txtScoreHome.setTextColor(context.color(R.color.darkBlue))
                        txtScoreAway.setTextColor(context.color(R.color.blue))
                    }
                    else -> {
                        txtScoreHome.setTextColor(context.color(R.color.darkBlue))
                        txtScoreAway.setTextColor(context.color(R.color.darkBlue))
                    }
                }
            }
        }
    }

}
