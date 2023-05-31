package com.lucas.soccerchallenge.ui.results

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.lucas.soccerchallenge.core.model.Match
import com.lucas.soccerchallenge.ui.base.Resource
import com.lucas.soccerchallenge.ui.home.HomeFragmentDirections
import com.lucas.soccerchallenge.ui.home.match.MatchFragment
import com.lucas.soccerchallenge.ui.results.adapter.ResultAdapter
import com.lucas.soccerchallenge.ui.results.adapter.toResultDisplayModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ResultsFragment : MatchFragment() {

    private val viewModel: ResultsViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var adapter: ResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        matchFilterViewModel.matchMapper = { match: Match -> match.toResultDisplayModel() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.matchResultsResponse.collect { response ->
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
        adapter.onMatchClick = { matchResult ->
            HomeFragmentDirections.actionHomeFragmentToMatchDetailFragment(matchResult.id)
                .also { navAction -> findNavController().navigate(navAction) }
        }
        // To keep scroll position when rotating device
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.apply {
            swipeRefresh.isEnabled = false
            swipeRefresh.setOnRefreshListener { viewModel.fetchMatchResults(true) }
            list.adapter = adapter
            errorRetry.retryBtn.setOnClickListener { viewModel.fetchMatchResults(true) }
        }
    }

    override fun adapterItemCount() = adapter.itemCount

    companion object {

        fun newInstance(): ResultsFragment {
            return ResultsFragment()
        }
    }

}
