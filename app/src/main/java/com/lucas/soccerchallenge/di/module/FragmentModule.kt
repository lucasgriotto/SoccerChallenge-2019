package com.lucas.soccerchallenge.di.module

import com.lucas.soccerchallenge.features.fixture.FixtureFragment
import com.lucas.soccerchallenge.features.results.ResultsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeFixtureFragment(): FixtureFragment

    @ContributesAndroidInjector
    abstract fun contributeResultsFragment(): ResultsFragment
}