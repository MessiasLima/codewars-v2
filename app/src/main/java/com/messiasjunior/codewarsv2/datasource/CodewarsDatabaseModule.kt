package com.messiasjunior.codewarsv2.datasource

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CodewarsDatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application): CodewarsDatabase {
        return Room.databaseBuilder(
            application,
            CodewarsDatabase::class.java,
            CODEWARS_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(codewarsDatabase: CodewarsDatabase) = codewarsDatabase.userDao()

    companion object {
        private const val CODEWARS_DATABASE_NAME = "codewars-database"
    }
}
