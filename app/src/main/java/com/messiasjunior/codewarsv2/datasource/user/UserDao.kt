package com.messiasjunior.codewarsv2.datasource.user

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.messiasjunior.codewarsv2.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(user: User)

    @Query("SELECT * FROM User WHERE username = :username")
    suspend fun findByUsername(username: String): User?

    @Query("SELECT * FROM User ORDER BY honor DESC LIMIT 5")
    fun findOrderedByHonor(): DataSource.Factory<Int, User>

    @Query("SELECT * FROM User ORDER BY searchDate DESC LIMIT 5")
    fun findOrderedBySearchDate(): DataSource.Factory<Int, User>
}
