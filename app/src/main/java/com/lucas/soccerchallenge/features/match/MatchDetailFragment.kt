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

// This fragment was created just to test passing parcelable arguments using navigation jetpack
class MatchDetailFragment : BaseFragment(R.layout.fragment_match_detail) {

    private val binding by viewBinding(FragmentMatchDetailBinding::bind)

    private val args: MatchDetailFragmentArgs by navArgs()

    private val viewModel: MatchDetailViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()
        args.matchFixture?.let { match ->
            binding.fixture.apply {
                competition.text = match.competitionName
                venue.text = match.venueName.plus(" | ")
                date.text = match.matchDate

                homeTeam.text = match.teamHomeName
                awayTeam.text = match.teamAwayName

                dayNum.text = match.dayNum
                dayName.text = match.dayName

                postponed.isVisible = match.isPostponed
                date.setTextColor(context.color(match.matchDateColor))
                root.isVisible = true
            }
        }

        args.matchResult?.let { match ->
            binding.result.apply {
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

}
