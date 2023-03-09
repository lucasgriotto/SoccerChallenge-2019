package com.lucas.soccerchallenge.features.home

import androidx.lifecycle.ViewModel
import com.lucas.soccerchallenge.di.scope.HomeFragmentScoped
import com.lucas.soccerchallenge.di.viewmodel.ViewModelKey
import com.lucas.soccerchallenge.features.fixture.FixtureFragment
import com.lucas.soccerchallenge.features.fixture.FixtureModule
import com.lucas.soccerchallenge.features.home.filter.FilterViewModel
import com.lucas.soccerchallenge.features.results.ResultsFragment
import com.lucas.soccerchallenge.features.results.ResultsModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class HomeModule {

    @Binds
    @IntoMap
    @ViewModelKey(FilterViewModel::class)
    abstract fun filterViewModel(viewModel: FilterViewModel): ViewModel

    @HomeFragmentScoped
    @ContributesAndroidInjector(modules = [FixtureModule::class])
    abstract fun fixtureFragment(): FixtureFragment

    @HomeFragmentScoped
    @ContributesAndroidInjector(modules = [ResultsModule::class])
    abstract fun resultsFragment(): ResultsFragment

}
