package com.messiasjunior.codewarsv2.datasource.challenge

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.messiasjunior.codewarsv2.model.Challenge
import okhttp3.internal.Internal

@Dao
interface ChallengeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(vararg challenge: Challenge)

    @Query("SELECT * FROM Challenge WHERE createdBy = :username")
    fun findByCreator(username: String): DataSource.Factory<Internal, Challenge>
}
