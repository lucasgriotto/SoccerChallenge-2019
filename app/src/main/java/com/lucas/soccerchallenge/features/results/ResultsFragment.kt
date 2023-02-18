package com.lucas.soccerchallenge.features.results

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.core.base.BaseFragment
import com.lucas.soccerchallenge.core.extension.viewBinding
import com.lucas.soccerchallenge.core.networking.AppError
import com.lucas.soccerchallenge.core.networking.Resource
import com.lucas.soccerchallenge.data.Match
import com.lucas.soccerchallenge.databinding.FragmentListBinding
import com.lucas.soccerchallenge.features.home.HomeFragmentDirections
import com.lucas.soccerchallenge.features.home.filter.FilterViewModel
import com.lucas.soccerchallenge.features.results.adapter.ResultAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class ResultsFragment : BaseFragment(R.layout.fragment_list) {

    private val binding by viewBinding(FragmentListBinding::bind)

    private val viewModel: ResultsViewModel by viewModels { viewModelFactory }

    private val filterViewModel: FilterViewModel by activityViewModels { viewModelFactory }

    @Inject
    lateinit var adapter: ResultAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.matchResultsResponse.collect { response ->
                    when (response) {
                        is Resource.Error -> displayErrorState(response.error)
                        is Resource.Loading -> displayLoadingState()
                        is Resource.Success -> displaySuccessState(response.data)
                        is Resource.Initialize -> Unit
                    }
                }
            }
        }
    }

    private fun initView() {
        binding.apply {
            adapter.onMatchClick = {
                HomeFragmentDirections.actionHomeFragmentToMatchDetailFragment(it)
                    .also { navAction ->
                        findNavController().navigate(navAction)
                    }
            }
            list.adapter = adapter
            errorRetry.retryBtn.setOnClickListener {
                viewModel.fetchMatchResults()
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
            adapter.setMatches(matches, filterViewModel.currentFilter)

            filterViewModel.filter
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { filters ->
                    adapter.setFilter(filters)
                }
        }
    }

    companion object {

        fun newInstance(): ResultsFragment {
            return ResultsFragment()
        }
    }

}
