package com.lucas.soccerchallenge.ui.results

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.lucas.soccerchallenge.ui.home.HomeFragmentDirections
import com.lucas.soccerchallenge.ui.home.match.MatchFragment
import com.lucas.soccerchallenge.ui.home.match.MatchScreenState
import com.lucas.soccerchallenge.ui.results.adapter.ResultAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class ResultsFragment : MatchFragment() {

    private val viewModel: ResultsViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var adapter: ResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchMatchResults(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.uiState.collect { response ->
                        when (response) {
                            is MatchScreenState.Error -> displayErrorState(response.error)
                            is MatchScreenState.Success -> displaySuccessState(response.data)
                            MatchScreenState.Loading -> displayLoadingState()
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
