package com.messiasjunior.codewarsv2.repository

import androidx.paging.PagedList
import com.messiasjunior.codewarsv2.model.Challenge
import com.messiasjunior.codewarsv2.util.runOnBackground

class ChallengeBoundaryCallback(
    private val loadChallenges: (page: Int) -> Boolean
) : PagedList.BoundaryCallback<Challenge>() {
    private var currentPage = 0

    override fun onItemAtEndLoaded(itemAtEnd: Challenge) {
        getRemoteChallengesAndSave(currentPage)
    }

    override fun onZeroItemsLoaded() {
        getRemoteChallengesAndSave(0)
    }

    private fun getRemoteChallengesAndSave(page: Int) = runOnBackground {
        val success = loadChallenges(page)
        if (success) currentPage++
    }
}
