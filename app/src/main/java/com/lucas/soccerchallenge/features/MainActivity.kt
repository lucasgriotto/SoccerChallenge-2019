package com.lucas.soccerchallenge.features

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.base.ui.BaseActivity
import com.lucas.soccerchallenge.features.filter.FilterDialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewPagerAdapter: MainViewPagerAdapter

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MainViewModel::class.java)

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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_filter -> {
                showFilterMenu()
                false
            }
            else -> false
        }
    }

    private fun showFilterMenu() {
        val dialog = FilterDialogFragment.newInstance()
        dialog.show(supportFragmentManager, null)
    }
}
