package com.messiasjunior.codewarsv2.datasource.challenge

import com.messiasjunior.codewarsv2.datasource.CodewarsAPI
import com.messiasjunior.codewarsv2.model.User
import javax.inject.Inject

class ChallengeRemoteDataSource @Inject constructor(
    private val codewarsAPI: CodewarsAPI
) {
    fun findCompletedChallenges(user: User, page: Int) = codewarsAPI.findCompletedChallenges(
        user.username,
        page
    )

    fun findAuthoredChallenges(user: User) = codewarsAPI.findAuthoredChallenges(user.username)

    companion object {
        const val DEFAULT_PAGE_SIZE = 200
    }
}
