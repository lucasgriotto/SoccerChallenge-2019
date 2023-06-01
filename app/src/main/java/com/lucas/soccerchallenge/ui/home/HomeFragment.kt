package com.lucas.soccerchallenge.ui.home

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.google.android.material.tabs.TabLayoutMediator
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.databinding.FragmentHomeBinding
import com.lucas.soccerchallenge.ui.base.BaseFragment
import com.lucas.soccerchallenge.ui.home.competitionfilter.CompetitionFilterAdapter
import com.lucas.soccerchallenge.ui.home.competitionfilter.CompetitionFilterViewModel
import com.lucas.soccerchallenge.utils.extension.viewBinding
import javax.inject.Inject

private const val FILTER_DURATION_ANIM_MS = 300L

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val competitionFilterViewModel: CompetitionFilterViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var competitionFilterAdapter: CompetitionFilterAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAppBarMenu()
        binding.apply {
            homeViewpager.offscreenPageLimit = 1
            homeViewpager.adapter = HomeFragmentStateAdapter(this@HomeFragment)
            TabLayoutMediator(homeTabLayout, homeViewpager) { tab, position ->
                tab.setText(HomeFragmentStateAdapter.tabs[position])
            }.attach()
            updateFilterAdapter()
            competitionFilter.filtersList.adapter = competitionFilterAdapter
            competitionFilter.applyBtn.setOnClickListener {
                competitionFilterViewModel.updateFilters(competitionFilterAdapter.filters)
                toggleFilterVisibility()
            }
            competitionFilter.filterContainer.setOnClickListener {
                toggleFilterVisibility()
            }
        }
    }

    private fun setupAppBarMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_main, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_filter -> {
                        toggleFilterVisibility()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun toggleFilterVisibility() {
        Slide(Gravity.END).also { slide ->
            slide.duration = FILTER_DURATION_ANIM_MS
            slide.addTarget(binding.competitionFilter.root)
            TransitionManager.beginDelayedTransition(binding.root, slide)
        }
        val isFilterVisible = binding.competitionFilter.root.isVisible
        binding.competitionFilter.root.isVisible = !isFilterVisible
        if (!isFilterVisible) {
            updateFilterAdapter()
        }
    }

    private fun updateFilterAdapter() {
        competitionFilterAdapter.setFilters(competitionFilterViewModel.allFilters)
    }

}
