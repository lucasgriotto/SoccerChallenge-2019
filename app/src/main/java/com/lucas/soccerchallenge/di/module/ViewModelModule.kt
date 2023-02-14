package com.lucas.soccerchallenge.di.module

import androidx.lifecycle.ViewModelProvider
import com.lucas.soccerchallenge.di.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
@Suppress("UNUSED")
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}
