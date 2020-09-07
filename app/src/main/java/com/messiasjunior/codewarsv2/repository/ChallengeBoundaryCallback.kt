package com.messiasjunior.codewarsv2.repository

import androidx.paging.PagedList
import com.messiasjunior.codewarsv2.model.Challenge
import com.messiasjunior.codewarsv2.util.runOnBackground

class ChallengeBoundaryCallback(
    private val loadChallenges: (page: Int) -> ResponseDetails,
    private val updateLoadingStatus: (isLoading: Boolean) -> Unit,
) : PagedList.BoundaryCallback<Challenge>() {
    private var currentPage = 0
    var reachedOnEndOfList = false
        private set

    override fun onItemAtEndLoaded(itemAtEnd: Challenge) {
        if (!reachedOnEndOfList) {
            getRemoteChallengesAndSave(currentPage)
        }
    }

    override fun onZeroItemsLoaded() {
        getRemoteChallengesAndSave(0)
    }

    private fun getRemoteChallengesAndSave(page: Int) = runOnBackground {
        updateLoadingStatus.invoke(true)
        val details = loadChallenges(page)
        updateLoadingStatus.invoke(false)
        if (details.success) currentPage++
        reachedOnEndOfList = details.endOfList
    }
}

data class ResponseDetails(
    val success: Boolean,
    val endOfList: Boolean
)
