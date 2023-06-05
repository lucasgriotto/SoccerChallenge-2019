package com.lucas.soccerchallenge.ui.match

import androidx.lifecycle.ViewModel
import com.lucas.soccerchallenge.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
@Suppress("UNUSED")
internal abstract class MatchDetailModule {

    @Binds
    @IntoMap
    @ViewModelKey(MatchDetailViewModel::class)
    abstract fun matchDetailViewModel(viewModel: MatchDetailViewModel): ViewModel

}
