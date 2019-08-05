package com.lucas.soccerchallenge.features

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.base.ui.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MainViewModel::class.java)

    }
}
