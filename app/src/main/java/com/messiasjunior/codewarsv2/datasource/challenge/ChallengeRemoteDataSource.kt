package com.messiasjunior.codewarsv2.datasource.challenge

import com.messiasjunior.codewarsv2.datasource.CodewarsAPI
import com.messiasjunior.codewarsv2.exception.ChallengeNotFoundException
import com.messiasjunior.codewarsv2.model.Challenge
import com.messiasjunior.codewarsv2.model.User
import retrofit2.awaitResponse
import javax.inject.Inject

class ChallengeRemoteDataSource @Inject constructor(
    private val codewarsAPI: CodewarsAPI
) {
    fun findCompletedChallenges(user: User, page: Int) = codewarsAPI.findCompletedChallenges(
        user.username,
        page
    )

    fun findAuthoredChallenges(user: User) = codewarsAPI.findAuthoredChallenges(user.username)

    suspend fun findChallengeById(id: String): Challenge {
        val challenge = codewarsAPI.findChallengeById(id).awaitResponse().body()
        return challenge ?: throw ChallengeNotFoundException("Challenge $id not found")
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 200
    }
}
