package com.messiasjunior.codewarsv2.datasource.user

import com.messiasjunior.codewarsv2.model.User
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun save(user: User) = userDao.save(user)

    suspend fun findByUsername(username: String) = userDao.findByUsername(username)
}
