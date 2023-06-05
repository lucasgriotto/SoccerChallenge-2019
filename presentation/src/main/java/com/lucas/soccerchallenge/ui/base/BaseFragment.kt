package com.lucas.soccerchallenge.ui.base

import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : DaggerFragment(contentLayoutId) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

}
