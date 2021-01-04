package com.lucas.soccerchallenge.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lucas.soccerchallenge.di.viewmodel.ViewModelFactory
import com.lucas.soccerchallenge.di.viewmodel.ViewModelKey
import com.lucas.soccerchallenge.features.filter.FilterDialogViewModel
import com.lucas.soccerchallenge.features.fixture.FixtureViewModel
import com.lucas.soccerchallenge.features.results.ResultsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
@Suppress("UNUSED")
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(FixtureViewModel::class)
    abstract fun bindsFixtureViewModel(viewModel: FixtureViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ResultsViewModel::class)
    abstract fun bindsResultsViewModel(viewModel: ResultsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FilterDialogViewModel::class)
    abstract fun bindsFilterDialogViewModel(viewModel: FilterDialogViewModel): ViewModel
}