package com.messiasjunior.codewarsv2.presentation.challenge

import com.messiasjunior.codewarsv2.di.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ChallengeDetailsModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeChallengeAndroidInjector(): ChallengeDetailsFragment
}
