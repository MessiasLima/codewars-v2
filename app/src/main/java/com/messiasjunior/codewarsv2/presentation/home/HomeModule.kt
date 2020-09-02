package com.messiasjunior.codewarsv2.presentation.home

import com.messiasjunior.codewarsv2.di.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeHomeAndroidInjector(): HomeFragment
}
