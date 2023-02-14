package com.lucas.soccerchallenge.features.fixture

import androidx.lifecycle.ViewModel
import com.lucas.soccerchallenge.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class FixtureModule {

    @Binds
    @IntoMap
    @ViewModelKey(FixtureViewModel::class)
    abstract fun fixtureViewModel(viewModel: FixtureViewModel): ViewModel

}
