package com.lucas.soccerchallenge.features.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.base.extension.viewBinding
import com.lucas.soccerchallenge.base.ui.BaseActivity
import com.lucas.soccerchallenge.databinding.ActivityMainBinding
import com.lucas.soccerchallenge.features.main.filter.FilterViewModel
import com.lucas.soccerchallenge.features.main.filter.FilterAdapter
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private val filterViewModel: FilterViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var filterAdapter: FilterAdapter

    @Inject
    lateinit var viewPagerAdapter: MainViewPagerAdapter
    override val contentLayout: View
        get() = binding.root

    private var btnApplyFilterClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.apply {
            viewpager.adapter = viewPagerAdapter
            tabLayout.setupWithViewPager(viewpager)

            drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

                override fun onDrawerOpened(drawerView: View) {
                    btnApplyFilterClicked = false
                    filterViewModel.backUpSelectedFilters(filterAdapter.selectedCompetitions)
                }

                override fun onDrawerClosed(drawerView: View) {
                    if (!btnApplyFilterClicked) {
                        filterAdapter.setBackedUpFilters(filterViewModel.backedUpFilter)
                    }
                }

                override fun onDrawerStateChanged(newState: Int) {}
            })
        }

        binding.partFilter.apply {
            mainList.adapter = filterAdapter

            btnApply.setOnClickListener {
                btnApplyFilterClicked = true
                filterAdapter.ifFiltersEmptySelectAll()
                filterViewModel.setFilters(filterAdapter.selectedCompetitions)
                closeFilterMenu()
            }
        }

        filterViewModel.getFilters()?.let {
            filterAdapter.selectedCompetitions = HashSet(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                openCloseFilterMenu()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openCloseFilterMenu() {
        binding.drawerLayout.let {
            if (!it.isDrawerOpen(GravityCompat.END))
                it.openDrawer(GravityCompat.END)
            else
                it.closeDrawer(GravityCompat.END)
        }
    }

    private fun closeFilterMenu() {
        binding.drawerLayout.let {
            if (it.isDrawerOpen(GravityCompat.END))
                it.closeDrawer(GravityCompat.END)
        }
    }

}