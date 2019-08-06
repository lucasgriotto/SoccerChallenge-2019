package com.lucas.soccerchallenge.features

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.base.ui.BaseActivity
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
}
