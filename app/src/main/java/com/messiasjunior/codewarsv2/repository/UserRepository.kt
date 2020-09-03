package com.messiasjunior.codewarsv2.repository

import com.messiasjunior.codewarsv2.datasource.user.UserLocalDataSource
import com.messiasjunior.codewarsv2.datasource.user.UserRemoteDataSource
import com.messiasjunior.codewarsv2.model.User
import com.messiasjunior.codewarsv2.util.resource.Resource
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) {
    suspend fun searchUser(query: String): Resource<User> {
        return try {
            Resource.success(userRemoteDataSource.searchUser(query))
        } catch (throwable: Throwable) {
            Resource.error(throwable = throwable)
        }
    }
}
