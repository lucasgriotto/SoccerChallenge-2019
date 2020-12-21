package com.lucas.soccerchallenge.features

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.base.ui.BaseActivity
import com.lucas.soccerchallenge.features.filter.FilterDialogFragment
import com.lucas.soccerchallenge.features.filter.FilterDialogViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewPagerAdapter: MainViewPagerAdapter

    private lateinit var filterViewModel: FilterDialogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        filterViewModel = ViewModelProvider(this, viewModelFactory)
            .get(FilterDialogViewModel::class.java)

        initView()
    }

    private fun initView() {
        viewpager.adapter = viewPagerAdapter
        tab_layout.setupWithViewPager(viewpager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                showFilterMenu()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showFilterMenu() {
        val dialog = FilterDialogFragment.newInstance()
        dialog.show(supportFragmentManager, null)
    }
}
