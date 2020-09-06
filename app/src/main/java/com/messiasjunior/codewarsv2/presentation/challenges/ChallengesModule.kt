package com.messiasjunior.codewarsv2.presentation.challenges

import com.messiasjunior.codewarsv2.di.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ChallengesModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeChallengesAndroidInjector(): ChallengesFragment
}
