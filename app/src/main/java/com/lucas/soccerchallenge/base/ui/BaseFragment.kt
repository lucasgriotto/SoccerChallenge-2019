package com.lucas.soccerchallenge.base.ui

import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : DaggerFragment(contentLayoutId) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    fun showView(view: View) {
        view.isVisible = true
    }

    fun hideView(view: View) {
        view.isVisible = false
    }
}