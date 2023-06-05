package com.lucas.soccerchallenge.di.module

import com.lucas.soccerchallenge.di.scope.FragmentScoped
import com.lucas.soccerchallenge.ui.home.HomeFragment
import com.lucas.soccerchallenge.ui.home.HomeModule
import com.lucas.soccerchallenge.ui.match.MatchDetailFragment
import com.lucas.soccerchallenge.ui.match.MatchDetailModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
@Suppress("UNUSED")
abstract class FragmentModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun homeFragment(): HomeFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [MatchDetailModule::class])
    abstract fun matchDetailFragment(): MatchDetailFragment

}
