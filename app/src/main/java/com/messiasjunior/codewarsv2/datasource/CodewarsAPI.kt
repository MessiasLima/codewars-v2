package com.messiasjunior.codewarsv2.datasource

import com.messiasjunior.codewarsv2.model.Challenge
import com.messiasjunior.codewarsv2.model.ChallengesApiResponse
import com.messiasjunior.codewarsv2.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CodewarsAPI {
    @GET("users/{query}")
    fun findUser(@Path("query") query: String): Call<User>

    @GET("users/{username}/code-challenges/completed")
    fun findCompletedChallenges(
        @Path("username") username: String,
        @Query("page") page: Int
    ): Call<ChallengesApiResponse>

    @GET("users/{username}/code-challenges/authored")
    fun findAuthoredChallenges(
        @Path("username") username: String
    ): Call<ChallengesApiResponse>

    @GET("code-challenges/{id}")
    fun findChallengeById(@Path("id") id: String): Call<Challenge>
}
