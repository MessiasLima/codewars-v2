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
    @Query("SELECT * FROM Challenge WHERE creatorUsername = :username ORDER BY _id")
    fun findByCreator(username: String): DataSource.Factory<Int, Challenge>

    @Query(
        """
        SELECT c.* 
        FROM Challenge c 
        INNER JOIN UserCompletedChallenge ucc 
            ON ucc.challengeId = c.codewarsID 
        WHERE ucc.username = :username
        ORDER BY c._id
        """
    )
    fun findCompletedChallenges(username: String): DataSource.Factory<Int, Challenge>

    @Query("SELECT * FROM Challenge WHERE codewarsID = :codewarsId")
    suspend fun findById(codewarsId: String): Challenge

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(challenge: List<Challenge>)

    @Update
    suspend fun update(vararg challenge: Challenge)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(
        challenges: List<Challenge>,
        userCompletedChallenges: MutableList<UserCompletedChallenge>
    )
}
