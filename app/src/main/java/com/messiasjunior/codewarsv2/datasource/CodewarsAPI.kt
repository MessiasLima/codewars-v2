package com.messiasjunior.codewarsv2.datasource

import com.messiasjunior.codewarsv2.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CodewarsAPI {
    @GET("users/{query}")
    fun findUser(@Path("query") query: String): Call<User>
}
