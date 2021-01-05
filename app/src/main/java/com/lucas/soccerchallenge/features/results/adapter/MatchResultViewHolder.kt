package com.lucas.soccerchallenge.features.results.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.base.extension.color
import com.lucas.soccerchallenge.data.model.Match
import com.lucas.soccerchallenge.databinding.ItemMatchResultBinding
import com.lucas.soccerchallenge.utils.DateUtils

class MatchResultViewHolder constructor(
    private val binding: ItemMatchResultBinding
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

        binding.result.apply {
            current?.let { cur ->
                txtCompetition.text = cur.competition.name
                txtVenueDate.text = cur.venueName
                    .plus(" | ")
                    .plus(DateUtils.getUIFormattedDate(cur.date))

                txtTeamHome.text = cur.homeTeam.name
                txtTeamAway.text = cur.awayTeam.name

                txtScoreHome.text = cur.score?.home.toString()
                txtScoreAway.text = cur.score?.away.toString()

                when {
                    cur.isHomeWinner() -> {
                        txtScoreHome.setTextColor(context.color(R.color.blue))
                        txtScoreAway.setTextColor(context.color(R.color.darkBlue))
                    }
                    cur.isAwayWinner() -> {
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

    companion object {

        fun create(parent: ViewGroup): MatchResultViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemMatchResultBinding.inflate(inflater, parent, false)
            return MatchResultViewHolder(binding)
        }
    }
}
