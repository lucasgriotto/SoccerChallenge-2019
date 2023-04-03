package com.lucas.soccerchallenge.ui.home

import androidx.lifecycle.ViewModel
import com.lucas.soccerchallenge.di.scope.HomeFragmentScoped
import com.lucas.soccerchallenge.di.viewmodel.ViewModelKey
import com.lucas.soccerchallenge.ui.fixture.FixtureFragment
import com.lucas.soccerchallenge.ui.fixture.FixtureModule
import com.lucas.soccerchallenge.ui.home.competitionfilter.CompetitionFilterViewModel
import com.lucas.soccerchallenge.ui.results.ResultsFragment
import com.lucas.soccerchallenge.ui.results.ResultsModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
@Suppress("UNUSED")
internal abstract class HomeModule {

    @Binds
    @IntoMap
    @ViewModelKey(CompetitionFilterViewModel::class)
    abstract fun filterViewModel(viewModel: CompetitionFilterViewModel): ViewModel

    @HomeFragmentScoped
    @ContributesAndroidInjector(modules = [FixtureModule::class])
    abstract fun fixtureFragment(): FixtureFragment

    @HomeFragmentScoped
    @ContributesAndroidInjector(modules = [ResultsModule::class])
    abstract fun resultsFragment(): ResultsFragment

}
