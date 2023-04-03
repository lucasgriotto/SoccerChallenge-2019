package com.lucas.soccerchallenge.ui.fixture

import androidx.lifecycle.ViewModel
import com.lucas.soccerchallenge.di.viewmodel.ViewModelKey
import com.lucas.soccerchallenge.ui.home.matchfilter.MatchFilterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
@Suppress("UNUSED")
internal abstract class FixtureModule {

    @Binds
    @IntoMap
    @ViewModelKey(FixtureViewModel::class)
    abstract fun fixtureViewModel(viewModel: FixtureViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MatchFilterViewModel::class)
    abstract fun matchFilterViewModel(viewModel: MatchFilterViewModel): ViewModel

}
