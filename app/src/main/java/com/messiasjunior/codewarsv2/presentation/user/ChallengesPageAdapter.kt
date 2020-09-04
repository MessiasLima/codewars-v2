package com.messiasjunior.codewarsv2.presentation.user

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.messiasjunior.codewarsv2.model.ChallengeType
import com.messiasjunior.codewarsv2.model.User
import com.messiasjunior.codewarsv2.presentation.challenges.ChallengesFragment

class ChallengesPageAdapter(
    fragmentManager: FragmentManager,
    private val user: User
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount() = ChallengeType.values().size

    override fun getItem(position: Int): Fragment {
        return ChallengesFragment.create(
            ChallengeType.values()[position],
            user
        )
    }
}
