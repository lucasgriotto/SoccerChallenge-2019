package com.lucas.soccerchallenge.features.results.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.data.model.Match
import com.lucas.soccerchallenge.utils.DateUtils
import kotlinx.android.synthetic.main.item_match_result.view.*
import kotlinx.android.synthetic.main.view_header.view.*
import kotlinx.android.synthetic.main.view_result.view.*

class MatchResultViewHolder private constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    fun bindTo(previous: Match?, current: Match?) {
        itemView.apply {
            current?.let { cur ->
                if (previous == null || !DateUtils.isSameMonthYear(previous.date, cur.date)) {
                    txt_month_year.text = DateUtils.getMonthYear(cur.date)
                    header.visibility = View.VISIBLE
                } else
                    header.visibility = View.GONE

                txt_competition.text = cur.competition.name
                txt_venue_date.text = cur.venueName
                    .plus(" | ")
                    .plus(DateUtils.getUIFormattedDate(cur.date))

                txt_team_home.text = cur.homeTeam.name
                txt_team_away.text = cur.awayTeam.name

                txt_score_home.text = cur.score?.home.toString()
                txt_score_away.text = cur.score?.away.toString()

                when {
                    cur.isHomeWinner() -> {
                        txt_score_home.setTextColor(getColor(R.color.blue))
                        txt_score_away.setTextColor(getColor(R.color.darkBlue))
                    }
                    cur.isAwayWinner() -> {
                        txt_score_home.setTextColor(getColor(R.color.darkBlue))
                        txt_score_away.setTextColor(getColor(R.color.blue))
                    }
                    else -> {
                        txt_score_home.setTextColor(getColor(R.color.darkBlue))
                        txt_score_away.setTextColor(getColor(R.color.darkBlue))
                    }
                }
            }
        }
    }

    private fun getColor(colorId: Int) = ContextCompat.getColor(itemView.context, colorId)

    companion object {

        fun create(parent: ViewGroup): MatchResultViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_match_result, parent, false)
            return MatchResultViewHolder(view)
        }
    }
}
