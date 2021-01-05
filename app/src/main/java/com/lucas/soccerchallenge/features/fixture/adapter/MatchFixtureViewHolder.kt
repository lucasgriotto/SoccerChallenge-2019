package com.lucas.soccerchallenge.features.fixture.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.base.extension.color
import com.lucas.soccerchallenge.data.model.Match
import com.lucas.soccerchallenge.databinding.ItemMatchFixtureBinding
import com.lucas.soccerchallenge.utils.DateUtils

class MatchFixtureViewHolder constructor(
    private val binding: ItemMatchFixtureBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindTo(previous: Match?, current: Match?) {
        val context = itemView.context

        binding.header.apply {
            current?.let { cur ->
                if (previous == null || !DateUtils.isSameMonthYear(previous.date, cur.date)) {
                    txtMonthYear.text = DateUtils.getMonthYear(cur.date)
                    root.isVisible = true
                } else {
                    root.isVisible = false
                }
            }
        }

        binding.fixture.apply {
            current?.let { cur ->
                txtCompetition.text = cur.competition.name
                txtVenue.text = cur.venueName.plus(" | ")
                txtDate.text = DateUtils.getUIFormattedDate(cur.date)

                txtTeamHome.text = cur.homeTeam.name
                txtTeamAway.text = cur.awayTeam.name

                txtDayNum.text = DateUtils.getMonthDayNumber(cur.date)
                txtDayName.text = DateUtils.getWeekDayNameShort(cur.date)

                if (cur.isPostponed()) {
                    txtPostponed.isVisible = true
                    txtDate.setTextColor(context.color(R.color.red))
                } else {
                    txtPostponed.isVisible = false
                    txtDate.setTextColor(context.color(R.color.grey))
                }
            }
        }
    }

    companion object {

        fun create(parent: ViewGroup): MatchFixtureViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemMatchFixtureBinding.inflate(inflater, parent, false)
            return MatchFixtureViewHolder(binding)
        }
    }
}
