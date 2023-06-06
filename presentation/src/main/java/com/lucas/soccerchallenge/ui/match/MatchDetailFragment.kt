package com.lucas.soccerchallenge.ui.match

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.databinding.FragmentMatchDetailBinding
import com.lucas.soccerchallenge.ui.base.BaseFragment
import com.lucas.soccerchallenge.ui.fixture.adapter.FixtureDisplayModel
import com.lucas.soccerchallenge.ui.match.TeamData.teamLogosMap
import com.lucas.soccerchallenge.ui.match.TeamData.teamUrlMap
import com.lucas.soccerchallenge.ui.results.adapter.ResultDisplayModel
import com.lucas.soccerchallenge.utils.DateUtils
import com.lucas.soccerchallenge.utils.Resource
import com.lucas.soccerchallenge.utils.extension.color
import com.lucas.soccerchallenge.utils.extension.loadImageUrl
import com.lucas.soccerchallenge.utils.extension.openWebPage
import com.lucas.soccerchallenge.utils.extension.viewBinding
import kotlinx.coroutines.launch

class MatchDetailFragment : BaseFragment(R.layout.fragment_match_detail) {

    private val binding by viewBinding(FragmentMatchDetailBinding::bind)

    private val args: MatchDetailFragmentArgs by navArgs()

    private val viewModel: MatchDetailViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchMatch(args.matchId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.fixture.collect { response ->
                        when (response) {
                            is Resource.Success -> displayMatchFixture(response.data)
                            else -> Unit
                        }
                    }
                }

                launch {
                    viewModel.result.collect { response ->
                        when (response) {
                            is Resource.Success -> displayMatchResult(response.data)
                            else -> Unit
                        }
                    }
                }
            }
        }
    }

    private fun displayMatchFixture(match: FixtureDisplayModel) {
        binding.apply {
            displayTeamsData(match.teamHomeId, match.teamHomeName, match.teamAwayId, match.teamAwayName)
            competition.text = match.competitionName
            venue.text = match.venueName.plus(" | ")
            date.text = DateUtils.getUIFormattedDate(match.date)

            dayNum.text = DateUtils.getMonthDayNumber(match.date)
            dayNum.isVisible = true
            dayName.text = DateUtils.getWeekDayNameShort(match.date)
            dayName.isVisible = true

            postponed.isVisible = match.isPostponed
            date.setTextColor(requireContext().color(match.matchDateColor))
        }
    }

    private fun displayMatchResult(match: ResultDisplayModel) {
        binding.apply {
            displayTeamsData(match.teamHomeId, match.teamHomeName, match.teamAwayId, match.teamAwayName)
            val context = requireContext()
            competition.text = match.competitionName
            venue.text = match.venueName.plus(" | ")
            date.text = DateUtils.getUIFormattedDate(match.date)
            val scoreText = SpannableString("${match.teamHomeScore} : ${match.teamAwayScore}")
            val scoreHomeColor = ForegroundColorSpan(context.color(match.scoreHomeColor))
            val scoreAwayColor = ForegroundColorSpan(context.color(match.scoreAwayColor))
            scoreText.setSpan(scoreHomeColor, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            scoreText.setSpan(scoreAwayColor, scoreText.lastIndex, scoreText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            score.text = scoreText
            score.isVisible = true
        }
    }

    private fun displayTeamsData(teamHomeId: Int, teamHomeName: String, teamAwayId: Int, teamAwayName: String) {
        binding.homeTeam.apply {
            teamLogo.loadImageUrl(teamLogosMap[teamHomeId], placeholder = R.drawable.ic_team_logo_placeholder)
            team.text = teamHomeName
            root.setOnClickListener {
                teamUrlMap[teamHomeId]?.let { requireActivity().openWebPage(it) }
            }
        }
        binding.awayTeam.apply {
            teamLogo.loadImageUrl(teamLogosMap[teamAwayId], placeholder = R.drawable.ic_team_logo_placeholder)
            team.text = teamAwayName
            root.setOnClickListener {
                teamUrlMap[teamAwayId]?.let { requireActivity().openWebPage(it) }
            }
        }
    }

}
