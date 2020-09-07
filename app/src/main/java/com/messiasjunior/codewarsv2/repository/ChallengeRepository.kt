package com.messiasjunior.codewarsv2.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.messiasjunior.codewarsv2.datasource.challenge.ChallengeLocalDataSource
import com.messiasjunior.codewarsv2.datasource.challenge.ChallengeRemoteDataSource
import com.messiasjunior.codewarsv2.model.Challenge
import com.messiasjunior.codewarsv2.model.ChallengeType
import com.messiasjunior.codewarsv2.model.User
import com.messiasjunior.codewarsv2.util.resource.Resource
import javax.inject.Inject

class ChallengeRepository @Inject constructor(
    private val challengeRemoteDataSource: ChallengeRemoteDataSource,
    private val challengeLocalDataSource: ChallengeLocalDataSource
) {

    fun findChallenges(
        user: User,
        challengeType: ChallengeType
    ): LiveData<Resource<PagedList<Challenge>>> {
        val resource = MediatorLiveData<Resource<PagedList<Challenge>>>()

        val config = Config(
            pageSize = CHALLENGES_DEFAULT_PAGE_SIZE,
            enablePlaceholders = true,
            maxSize = CHALLENGES_MAX_ELEMENTS_IN_MEMORY
        )

        val boundaryCallback = ChallengeBoundaryCallback(
            { page -> this.loadAndSaveChallenges(page, challengeType, user) },
            { resource.postValue(Resource.loading(shouldShowLoading = it)) },
            { resource.postValue(Resource.error(throwable = it)) }
        )

        val dataSourceFactory = when (challengeType) {
            ChallengeType.COMPLETED -> challengeLocalDataSource.findCompletedChallenges(user)
            ChallengeType.AUTHORED -> challengeLocalDataSource.findAuthoredChallenges(user)
        }

        val pagedListLiveData = dataSourceFactory.toLiveData(
            config = config,
            boundaryCallback = boundaryCallback
        )

        resource.addSource(pagedListLiveData) {
            resource.value = Resource.success(it, boundaryCallback.reachedOnEndOfList)
        }

        return resource
    }

    private fun loadAndSaveChallenges(
        page: Int,
        challengeType: ChallengeType,
        user: User
    ): ResponseDetails {
        return try {
            when (challengeType) {
                ChallengeType.COMPLETED -> loadAndSaveCompletedChallenges(user, page)
                ChallengeType.AUTHORED -> loadAndSaveAuthoredChallenges(user)
            }
        } catch (throwable: Throwable) {
            ResponseDetails(success = false, throwable = throwable)
        }
    }

    private fun loadAndSaveCompletedChallenges(user: User, page: Int): ResponseDetails {
        val response = challengeRemoteDataSource.findCompletedChallenges(user, page)
            .execute()
            .body()

        return if (response != null) {
            challengeLocalDataSource.saveCompletedChallenges(response.data, user)
            ResponseDetails(
                success = true,
                endOfList = response.data.size < ChallengeRemoteDataSource.DEFAULT_PAGE_SIZE
            )
        } else {
            ResponseDetails(success = false, endOfList = false)
        }
    }

    private fun loadAndSaveAuthoredChallenges(user: User): ResponseDetails {
        val response = challengeRemoteDataSource.findAuthoredChallenges(user)
            .execute()
            .body()

        return if (response != null) {
            challengeLocalDataSource.saveAuthoredChallenges(response.data, user)
            ResponseDetails(
                success = true,
                endOfList = true
            )
        } else {
            ResponseDetails(success = false, endOfList = false)
        }
    }

    suspend fun getChallengeDetails(id: String): Resource<Challenge> {
        return try {
            var savedChallenge = challengeLocalDataSource.findChallengeById(id)

            if (savedChallenge.description.isNullOrBlank()) {
                val remoteChallenge = challengeRemoteDataSource.findChallengeById(id)
                savedChallenge = savedChallenge.copy(description = remoteChallenge.description)
                challengeLocalDataSource.update(savedChallenge)
            }

            Resource.success(savedChallenge)
        } catch (throwable: Throwable) {
            Resource.error(throwable = throwable)
        }
    }

    companion object {
        private const val CHALLENGES_DEFAULT_PAGE_SIZE = 20
        private const val CHALLENGES_MAX_ELEMENTS_IN_MEMORY = 200
    }
}
