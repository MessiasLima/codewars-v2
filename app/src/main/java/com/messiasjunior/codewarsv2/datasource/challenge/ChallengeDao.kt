package com.messiasjunior.codewarsv2.datasource.challenge

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.messiasjunior.codewarsv2.model.Challenge
import com.messiasjunior.codewarsv2.model.UserCompletedChallenge

@Dao
interface ChallengeDao {
    @Query("SELECT * FROM Challenge WHERE creatorUsername = :username")
    fun findByCreator(username: String): DataSource.Factory<Int, Challenge>

    @Query("SELECT * FROM Challenge c INNER JOIN UserCompletedChallenge ucc ON ucc.challengeId = c.id WHERE ucc.username = :username")
    fun findCompletedChallenges(username: String): DataSource.Factory<Int, Challenge>

    @Query("SELECT * FROM Challenge WHERE id = :id")
    suspend fun findById(id: String): Challenge

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(challenge: List<Challenge>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg challenge: Challenge)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(
        challenges: List<Challenge>,
        userCompletedChallenges: MutableList<UserCompletedChallenge>
    )
}
