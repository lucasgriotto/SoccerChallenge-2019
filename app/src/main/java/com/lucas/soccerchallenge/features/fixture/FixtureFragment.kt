package com.lucas.soccerchallenge.features.fixture

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.core.base.BaseFragment
import com.lucas.soccerchallenge.core.extension.viewBinding
import com.lucas.soccerchallenge.core.networking.AppError
import com.lucas.soccerchallenge.core.networking.Resource
import com.lucas.soccerchallenge.data.Match
import com.lucas.soccerchallenge.databinding.FragmentListBinding
import com.lucas.soccerchallenge.features.fixture.adapter.FixtureAdapter
import com.lucas.soccerchallenge.features.fixture.adapter.toFixtureDisplayModel
import com.lucas.soccerchallenge.features.home.HomeFragmentDirections
import com.lucas.soccerchallenge.features.home.competitionfilter.CompetitionFilterViewModel
import com.lucas.soccerchallenge.features.home.match.MatchFilterViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class FixtureFragment : BaseFragment(R.layout.fragment_list) {

    private val binding by viewBinding(FragmentListBinding::bind)

    private val viewModel: FixtureViewModel by viewModels { viewModelFactory }

    private val matchFilterViewModel: MatchFilterViewModel by viewModels { viewModelFactory }

    private val competitionFilterViewModel: CompetitionFilterViewModel by activityViewModels { viewModelFactory }

    @Inject
    lateinit var adapter: FixtureAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.fixtureResponse.collect { response ->
                        when (response) {
                            is Resource.Error -> displayErrorState(response.error)
                            is Resource.Loading -> displayLoadingState()
                            is Resource.Success -> displaySuccessState(response.data)
                            is Resource.Initialize -> Unit
                        }
                    }
                }

                launch {
                    matchFilterViewModel.filteredMatches.collect { matches ->
                        adapter.setMatches(matches)
                    }
                }
            }
        }
    }

    private fun initView() {
        adapter.onMatchClick = { matchFixture ->
            HomeFragmentDirections.actionHomeFragmentToMatchDetailFragment(matchFixture = matchFixture)
                .also { navAction ->
                    findNavController().navigate(navAction)
                }
        }
        // To keep scroll position when rotating device
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.apply {
            list.adapter = adapter
            errorRetry.retryBtn.setOnClickListener {
                viewModel.fetchFixture()
            }
        }
    }

    private fun displayLoadingState() {
        binding.apply {
            showView(listLoading.root)
            hideView(errorRetry.root)
        }
    }

    private fun displayErrorState(error: AppError) {
        val errorMessage = error.getErrorMessage(requireContext())
        binding.apply {
            hideView(listLoading.root)
            errorRetry.errorMessage.text = errorMessage
            showView(errorRetry.root)
        }
    }

    private fun displaySuccessState(matches: List<Match>) {
        binding.apply {
            hideView(listLoading.root)
            hideView(errorRetry.root)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            matchFilterViewModel.filterMatches(matches, competitionFilterViewModel.selectedFilters) {
                it.toFixtureDisplayModel()
            }

            competitionFilterViewModel.filter
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { filters ->
                    matchFilterViewModel.filterMatches(filters = filters) { it.toFixtureDisplayModel() }
                }
        }
    }

    companion object {

        fun newInstance(): FixtureFragment {
            return FixtureFragment()
        }
    }

}
