package com.lucas.soccerchallenge.features.navigation

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.core.base.BaseActivity
import com.lucas.soccerchallenge.core.extension.viewBinding
import com.lucas.soccerchallenge.databinding.ActivityNavigationBinding

class NavigationActivity : BaseActivity() {

    private val binding by viewBinding(ActivityNavigationBinding::inflate)

    private lateinit var navController: NavController

    override val contentLayout: View
        get() = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        binding.apply {
            setSupportActionBar(toolbar)
            setupActionBarWithNavController(navController, AppBarConfiguration(navController.graph))
        }
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

}
