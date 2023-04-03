package com.lucas.soccerchallenge.di.module

import com.lucas.soccerchallenge.ui.navigation.NavigationActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
@Suppress("UNUSED")
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun navigationActivity(): NavigationActivity
}