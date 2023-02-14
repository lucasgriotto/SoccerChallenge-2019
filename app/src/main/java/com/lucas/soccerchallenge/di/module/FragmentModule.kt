package com.lucas.soccerchallenge.di.module

import com.lucas.soccerchallenge.di.scope.FragmentScoped
import com.lucas.soccerchallenge.features.home.HomeFragment
import com.lucas.soccerchallenge.features.home.HomeModule
import com.lucas.soccerchallenge.features.match.MatchDetailFragment
import com.lucas.soccerchallenge.features.match.MatchDetailModule
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
