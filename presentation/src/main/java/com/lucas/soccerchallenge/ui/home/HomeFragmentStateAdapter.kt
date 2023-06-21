package com.lucas.soccerchallenge.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.ui.fixture.FixtureFragment
import com.lucas.soccerchallenge.ui.results.ResultsFragment

class HomeFragmentStateAdapter(homeFragment: HomeFragment) :
        FragmentStateAdapter(homeFragment) {

    companion object {
        val tabs = listOf(
                R.string.fixture,
                R.string.results
        )
    }

    override fun getItemCount(): Int {
        return tabs.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FixtureFragment.newInstance()
            else -> ResultsFragment.newInstance()
        }
    }

}
