package com.messiasjunior.codewarsv2.datasource

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class CodewarsApiModule {
    @Provides
    fun providesCodewarsAPI(): CodewarsAPI {
        return Retrofit.Builder()
            .baseUrl(CODEWARS_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CodewarsAPI::class.java)
    }

    companion object {
        private const val CODEWARS_API_BASE_URL = "https://www.codewars.com/api/v1/"
    }
}
