package com.messiasjunior.codewarsv2.datasource.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.messiasjunior.codewarsv2.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(user: User)

    @Query("SELECT * from User where username = :username")
    suspend fun findByUsername(username: String): User?
}
