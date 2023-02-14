package com.lucas.soccerchallenge.features.results

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
        subscribeToLiveData()
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

    private fun subscribeToLiveData() {
        viewModel.getResultResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    displayLoadingState()
                }
                is Resource.Error -> {
                    displayErrorState(getAppError(response.error))
                }
                is Resource.Success -> {
                    displaySuccessState(response.data)
                }
            }
        }

        filterViewModel.filter.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.setFilter(it)
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
        binding.apply {
            hideView(listLoading.root)
            errorRetry.errorMessage.text = error.message
            showView(errorRetry.root)
        }
    }

    private fun displaySuccessState(matches: List<Match>) {
        binding.apply {
            hideView(listLoading.root)
            hideView(errorRetry.root)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.setMatches(matches)
        }
    }

    companion object {

        fun newInstance(): ResultsFragment {
            return ResultsFragment()
        }
    }

}
