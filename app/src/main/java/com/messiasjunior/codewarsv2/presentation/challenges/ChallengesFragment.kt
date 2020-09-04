package com.messiasjunior.codewarsv2.presentation.challenges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.messiasjunior.codewarsv2.R
import com.messiasjunior.codewarsv2.model.ChallengeType
import com.messiasjunior.codewarsv2.model.User

class ChallengesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_challenges, container, false)
    }

    companion object {
        private const val ARGUMENT_CHALLENGE_TYPE = "challengeType"
        private const val ARGUMENT_USER = "user"

        fun create(challengeType: ChallengeType, user: User): ChallengesFragment {
            val fragment = ChallengesFragment()
            fragment.arguments = bundleOf(
                Pair(ARGUMENT_CHALLENGE_TYPE, challengeType),
                Pair(ARGUMENT_USER, user)
            )
            return fragment
        }
    }
}
