package com.messiasjunior.codewarsv2.datasource.challenge

import com.messiasjunior.codewarsv2.model.Challenge
import com.messiasjunior.codewarsv2.model.User
import com.messiasjunior.codewarsv2.model.UserCompletedChallenge
import javax.inject.Inject

class ChallengeLocalDataSource @Inject constructor(
    private val challengeDao: ChallengeDao
) {
    fun findAuthoredChallenges(user: User) = challengeDao.findByCreator(user.username)

    fun findCompletedChallenges(user: User) = challengeDao.findCompletedChallenges(user.username)

    fun saveAuthoredChallenges(challenges: List<Challenge>, user: User) {
        val challengesWithCreator = challenges
            .filter { it.name != null }
            .map { it.copy(creatorUsername = user.username) }

        challengeDao.save(challengesWithCreator)
    }

    fun saveCompletedChallenges(challenges: List<Challenge>, user: User) {
        val userCompletedChallenges = mutableListOf<UserCompletedChallenge>()

        challenges
            .filter { it.name != null }
            .forEach {
                userCompletedChallenges.add(
                    UserCompletedChallenge(
                        user.username,
                        it.codewarsID
                    )
                )
            }

        challengeDao.save(challenges, userCompletedChallenges)
    }

    suspend fun findChallengeById(id: String) = challengeDao.findById(id)

    suspend fun update(challenge: Challenge) = challengeDao.update(challenge)
}
