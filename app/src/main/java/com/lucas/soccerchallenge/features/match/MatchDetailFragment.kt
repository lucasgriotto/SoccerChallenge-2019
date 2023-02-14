package com.lucas.soccerchallenge.features.match

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.core.base.BaseFragment
import com.lucas.soccerchallenge.core.extension.color
import com.lucas.soccerchallenge.core.extension.viewBinding
import com.lucas.soccerchallenge.databinding.FragmentMatchDetailBinding
import com.lucas.soccerchallenge.utils.DateUtils

class MatchDetailFragment : BaseFragment(R.layout.fragment_match_detail) {

    private val binding by viewBinding(FragmentMatchDetailBinding::bind)

    private val args: MatchDetailFragmentArgs by navArgs()

    private val viewModel: MatchDetailViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val match = args.match
        if (match.score != null) {
            binding.matchDetailResult.apply {
                txtCompetition.text = match.competition.name
                txtVenueDate.text = match.venueName
                    .plus(" | ")
                    .plus(DateUtils.getUIFormattedDate(match.date))

                txtTeamHome.text = match.homeTeam.name
                txtTeamAway.text = match.awayTeam.name

                txtScoreHome.text = match.score.home.toString()
                txtScoreAway.text = match.score.away.toString()

                when {
                    match.isHomeWinner -> {
                        txtScoreHome.setTextColor(requireContext().color(R.color.blue))
                        txtScoreAway.setTextColor(requireContext().color(R.color.darkBlue))
                    }
                    match.isAwayWinner -> {
                        txtScoreHome.setTextColor(requireContext().color(R.color.darkBlue))
                        txtScoreAway.setTextColor(requireContext().color(R.color.blue))
                    }
                    else -> {
                        txtScoreHome.setTextColor(requireContext().color(R.color.darkBlue))
                        txtScoreAway.setTextColor(requireContext().color(R.color.darkBlue))
                    }
                }
                root.isVisible = true
            }
        } else {
            binding.matchDetailFixture.apply {
                txtCompetition.text = match.competition.name
                txtVenue.text = match.venueName.plus(" | ")
                txtDate.text = DateUtils.getUIFormattedDate(match.date)

                txtTeamHome.text = match.homeTeam.name
                txtTeamAway.text = match.awayTeam.name

                txtDayNum.text = DateUtils.getMonthDayNumber(match.date)
                txtDayName.text = DateUtils.getWeekDayNameShort(match.date)

                if (match.isPostponed) {
                    txtPostponed.isVisible = true
                    txtDate.setTextColor(requireContext().color(R.color.red))
                } else {
                    txtPostponed.isVisible = false
                    txtDate.setTextColor(requireContext().color(R.color.grey))
                }
                root.isVisible = true
            }
        }
    }

}
