package com.lucas.soccerchallenge.ui.home.match

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.databinding.FragmentListBinding
import com.lucas.soccerchallenge.ui.base.BaseFragment
import com.lucas.soccerchallenge.ui.home.competitionfilter.CompetitionFilterViewModel
import com.lucas.soccerchallenge.ui.home.matchfilter.MatchFilterViewModel
import com.lucas.soccerchallenge.ui.home.matchfilter.model.MatchDisplayModel
import com.lucas.soccerchallenge.utils.errorfactory.AppError
import com.lucas.soccerchallenge.utils.extension.viewBinding
import kotlinx.coroutines.launch

abstract class MatchFragment : BaseFragment(R.layout.fragment_list) {

    protected val binding by viewBinding(FragmentListBinding::bind)

    protected val matchFilterViewModel: MatchFilterViewModel by viewModels { viewModelFactory }

    private val competitionFilterViewModel: CompetitionFilterViewModel by viewModels(
            ownerProducer = { requireParentFragment() }
    ) { viewModelFactory }

    abstract fun adapterItemCount(): Int

    protected fun displayLoadingState() {
        binding.apply {
            if (!swipeRefresh.isRefreshing) {
                listLoading.root.isVisible = true
            }
            errorRetry.root.isVisible = false
        }
    }

    protected fun displayErrorState(error: AppError) {
        val errorMessage = error.getErrorMessage(requireContext())
        binding.apply {
            listLoading.root.isVisible = false
            errorRetry.errorMessage.text = errorMessage
            errorRetry.root.isVisible = true
            if (adapterItemCount() == 0) {
                errorRetry.root.setOnClickListener(null)
                errorRetry.retryBtn.isVisible = true
                swipeRefresh.isEnabled = false
            } else {
                errorRetry.root.setOnClickListener { errorRetry.root.isVisible = false }
                errorRetry.retryBtn.isVisible = false
                swipeRefresh.isEnabled = true
            }
            swipeRefresh.isRefreshing = false
        }
    }

    protected fun displaySuccessState(matches: List<MatchDisplayModel>) {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.apply {
                if (listLoading.root.isVisible || swipeRefresh.isRefreshing) {
                    matchFilterViewModel.filterMatches(matches, competitionFilterViewModel.selectedFiltersIds)
                }
                swipeRefresh.isEnabled = true
                swipeRefresh.isRefreshing = false
                listLoading.root.isVisible = false
                errorRetry.root.isVisible = false
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            competitionFilterViewModel.filterIds.flowWithLifecycle(
                    viewLifecycleOwner.lifecycle,
                    Lifecycle.State.STARTED
            ).collect { filters ->
                matchFilterViewModel.filterMatches(filtersIds = filters)
            }
        }
    }

}
