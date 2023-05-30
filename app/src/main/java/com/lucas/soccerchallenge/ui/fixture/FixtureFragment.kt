package com.lucas.soccerchallenge.ui.fixture

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.core.model.Match
import com.lucas.soccerchallenge.databinding.FragmentListBinding
import com.lucas.soccerchallenge.ui.base.BaseFragment
import com.lucas.soccerchallenge.ui.base.Resource
import com.lucas.soccerchallenge.ui.fixture.adapter.FixtureAdapter
import com.lucas.soccerchallenge.ui.fixture.adapter.toFixtureDisplayModel
import com.lucas.soccerchallenge.ui.home.HomeFragmentDirections
import com.lucas.soccerchallenge.ui.home.competitionfilter.CompetitionFilterViewModel
import com.lucas.soccerchallenge.ui.home.matchfilter.MatchFilterViewModel
import com.lucas.soccerchallenge.utils.errorfactory.AppError
import com.lucas.soccerchallenge.utils.extension.viewBinding
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
            HomeFragmentDirections.actionHomeFragmentToMatchDetailFragment(matchFixture.id)
                .also { navAction -> findNavController().navigate(navAction) }
        }
        // To keep scroll position when rotating device
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.apply {
            swipeRefresh.isEnabled = false
            swipeRefresh.setOnRefreshListener { viewModel.fetchFixture(true) }
            list.adapter = adapter
            errorRetry.retryBtn.setOnClickListener { viewModel.fetchFixture(true) }
        }
    }

    private fun displayLoadingState() {
        binding.apply {
            if (!swipeRefresh.isRefreshing) {
                showView(listLoading.root)
            }
            hideView(errorRetry.root)
        }
    }

    private fun displayErrorState(error: AppError) {
        val errorMessage = error.getErrorMessage(requireContext())
        binding.apply {
            hideView(listLoading.root)
            errorRetry.errorMessage.text = errorMessage
            errorRetry.retryBtn.isVisible = !swipeRefresh.isRefreshing
            showView(errorRetry.root)
            if (swipeRefresh.isRefreshing) {
                errorRetry.root.setOnClickListener { hideView(errorRetry.root) }
            } else {
                errorRetry.root.setOnClickListener(null)
            }
            swipeRefresh.isRefreshing = false
        }
    }

    private fun displaySuccessState(matches: List<Match>) {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.apply {
                if (listLoading.root.isVisible || swipeRefresh.isRefreshing) {
                    matchFilterViewModel.filterMatches(matches, competitionFilterViewModel.selectedFilters) {
                        it.toFixtureDisplayModel()
                    }
                }

                swipeRefresh.isEnabled = true
                swipeRefresh.isRefreshing = false
                hideView(listLoading.root)
                hideView(errorRetry.root)
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
