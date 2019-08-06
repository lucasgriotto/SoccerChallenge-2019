package com.lucas.soccerchallenge.features.fixture.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.data.model.Match
import com.lucas.soccerchallenge.utils.DateUtils
import kotlinx.android.synthetic.main.item_match_fixture.view.*
import kotlinx.android.synthetic.main.view_fixture.view.*
import kotlinx.android.synthetic.main.view_header.view.*

class MatchFixtureViewHolder private constructor(itemView: View) :
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
                txt_venue.text = cur.venueName.plus(" | ")
                txt_date.text = DateUtils.getUIFormattedDate(cur.date)

                txt_team_home.text = cur.homeTeam.name
                txt_team_away.text = cur.awayTeam.name

                txt_day_num.text = DateUtils.getMonthDayNumber(cur.date)
                txt_day_name.text = DateUtils.getWeekDayNameShort(cur.date)

                if (cur.isPostponed()) {
                    txt_postponed.visibility = View.VISIBLE
                    txt_date.setTextColor(getColor(R.color.red))
                } else {
                    txt_postponed.visibility = View.GONE
                    txt_date.setTextColor(getColor(R.color.grey))
                }
            }
        }
    }

    private fun getColor(colorId: Int) = ContextCompat.getColor(itemView.context, colorId)

    companion object {

        fun create(parent: ViewGroup): MatchFixtureViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_match_fixture, parent, false)
            return MatchFixtureViewHolder(view)
        }
    }
}
