package com.messiasjunior.codewarsv2.di

import android.app.Application
import com.messiasjunior.codewarsv2.datasource.CodewarsApiModule
import com.messiasjunior.codewarsv2.datasource.CodewarsDatabaseModule
import com.messiasjunior.codewarsv2.presentation.home.HomeModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        AndroidInjectionModule::class,

        // Data sources
        CodewarsApiModule::class,
        CodewarsDatabaseModule::class,

        // Fragments
        HomeModule::class
    ]
)
@Singleton
interface CodewarsApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): CodewarsApplicationComponent
    }

    fun inject(application: CodewarsApplication)
}
