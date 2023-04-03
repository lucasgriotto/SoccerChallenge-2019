package com.lucas.soccerchallenge.ui.results

import androidx.lifecycle.ViewModel
import com.lucas.soccerchallenge.di.viewmodel.ViewModelKey
import com.lucas.soccerchallenge.ui.home.matchfilter.MatchFilterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
@Suppress("UNUSED")
internal abstract class ResultsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ResultsViewModel::class)
    abstract fun bindsResultsViewModel(viewModel: ResultsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MatchFilterViewModel::class)
    abstract fun matchFilterViewModel(viewModel: MatchFilterViewModel): ViewModel

}
