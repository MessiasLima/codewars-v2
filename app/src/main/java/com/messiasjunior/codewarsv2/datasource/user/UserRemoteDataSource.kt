package com.messiasjunior.codewarsv2.datasource.user

import com.messiasjunior.codewarsv2.datasource.CodewarsAPI
import com.messiasjunior.codewarsv2.exception.UserNotFountException
import com.messiasjunior.codewarsv2.model.User
import retrofit2.awaitResponse
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val codewarsAPI: CodewarsAPI
) {
    suspend fun searchUser(query: String): User {
        val user = codewarsAPI.findUser(query).awaitResponse().body()
        return user ?: throw UserNotFountException(query)
    }
}
