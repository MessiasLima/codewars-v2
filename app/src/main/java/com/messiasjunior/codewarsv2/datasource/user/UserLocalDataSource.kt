package com.messiasjunior.codewarsv2.datasource.user

import com.messiasjunior.codewarsv2.model.User
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val userDao: UserDao
) {
    fun save(user: User) = userDao.save(user)
}
