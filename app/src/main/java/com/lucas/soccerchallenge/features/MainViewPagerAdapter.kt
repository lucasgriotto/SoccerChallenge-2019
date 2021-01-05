package com.lucas.soccerchallenge.features

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.features.fixture.FixtureFragment
import com.lucas.soccerchallenge.features.results.ResultsFragment
import javax.inject.Inject

class MainViewPagerAdapter @Inject constructor(
    val context: Context,
    activity: MainActivity
) : FragmentStatePagerAdapter(
    activity.supportFragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    companion object {
        private const val PAGE_COUNT = 2
    }

    private val tabTitles: Array<String> = arrayOf(
        context.getString(R.string.fixture),
        context.getString(R.string.results)
    )

    override fun getItem(position: Int): Fragment {
        return getFragments(position)
    }

    private fun getFragments(position: Int): Fragment {
        return when (position) {
            0 -> FixtureFragment.newInstance()
            else -> ResultsFragment.newInstance()
        }
    }

    override fun getCount(): Int = PAGE_COUNT

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }
}
