package com.messiasjunior.codewarsv2.datasource.user

import androidx.room.Dao
import androidx.room.Insert
import com.messiasjunior.codewarsv2.model.User

@Dao
interface UserDao {
    @Insert
    fun save(user: User)
}
