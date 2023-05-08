package com.lucas.soccerchallenge.ui.match

import android.os.Bundle
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
import com.lucas.soccerchallenge.ui.base.Resource
import com.lucas.soccerchallenge.ui.fixture.adapter.FixtureDisplayModel
import com.lucas.soccerchallenge.ui.results.adapter.ResultDisplayModel
import com.lucas.soccerchallenge.utils.extension.color
import com.lucas.soccerchallenge.utils.extension.viewBinding
import kotlinx.coroutines.launch

// This fragment was created just to test passing arguments using navigation jetpack
class MatchDetailFragment : BaseFragment(R.layout.fragment_match_detail) {

    private val binding by viewBinding(FragmentMatchDetailBinding::bind)

    private val args: MatchDetailFragmentArgs by navArgs()

    private val viewModel: MatchDetailViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.fixtureResponse.collect { response ->
                        when (response) {
                            is Resource.Success -> displayMatchFixture(response.data)
                            else -> Unit
                        }
                    }
                }

                launch {
                    viewModel.resultResponse.collect { response ->
                        when (response) {
                            is Resource.Success -> displayMatchResult(response.data)
                            else -> Unit
                        }
                    }
                }
            }
        }
        viewModel.fetchMatch(args.matchId)
    }

    private fun displayMatchFixture(match: FixtureDisplayModel) {
        binding.fixture.apply {
            competition.text = match.competitionName
            venue.text = match.venueName.plus(" | ")
            date.text = match.matchDate

            homeTeam.text = match.teamHomeName
            awayTeam.text = match.teamAwayName

            dayNum.text = match.dayNum
            dayName.text = match.dayName

            postponed.isVisible = match.isPostponed
            date.setTextColor(requireContext().color(match.matchDateColor))
            root.isVisible = true
        }
    }

    private fun displayMatchResult(match: ResultDisplayModel) {
        binding.result.apply {
            val context = requireContext()
            competition.text = match.competitionName
            venueDate.text = match.venueName.plus(" | ").plus(match.matchDate)

            homeTeam.text = match.teamHomeName
            awayTeam.text = match.teamAwayName

            homeTeamScore.text = match.teamHomeScore
            homeTeamScore.setTextColor(context.color(match.scoreHomeColor))

            awayTeamScore.text = match.teamAwayScore
            awayTeamScore.setTextColor(context.color(match.scoreAwayColor))
            root.isVisible = true
        }
    }

}
