package com.lucas.soccerchallenge.features.fixture.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.core.extension.color
import com.lucas.soccerchallenge.data.Match
import com.lucas.soccerchallenge.databinding.ItemMatchFixtureBinding
import com.lucas.soccerchallenge.features.home.filter.MatchFilter
import com.lucas.soccerchallenge.utils.DateUtils
import javax.inject.Inject

class FixtureAdapter @Inject constructor(
    matchFilter: MatchFilter
) : MatchFilterAdapter<FixtureAdapter.ViewHolder>(matchFilter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMatchFixtureBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder constructor(
        private val binding: ItemMatchFixtureBinding
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

            binding.fixture.apply {
                root.setOnClickListener {
                    onMatchClick(current)
                }
                txtCompetition.text = current.competition.name
                txtVenue.text = current.venueName.plus(" | ")
                txtDate.text = DateUtils.getUIFormattedDate(current.date)

                txtTeamHome.text = current.homeTeam.name
                txtTeamAway.text = current.awayTeam.name

                txtDayNum.text = DateUtils.getMonthDayNumber(current.date)
                txtDayName.text = DateUtils.getWeekDayNameShort(current.date)

                if (current.isPostponed) {
                    txtPostponed.isVisible = true
                    txtDate.setTextColor(context.color(R.color.red))
                } else {
                    txtPostponed.isVisible = false
                    txtDate.setTextColor(context.color(R.color.grey))
                }
            }
        }
    }

}
