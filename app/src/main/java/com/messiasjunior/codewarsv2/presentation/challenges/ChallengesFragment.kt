package com.messiasjunior.codewarsv2.presentation.challenges

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.messiasjunior.codewarsv2.R
import com.messiasjunior.codewarsv2.model.ChallengeType
import com.messiasjunior.codewarsv2.model.User
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ChallengesFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ChallengesViewModel.Factory
    private val viewModel by viewModels<ChallengesViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_challenges, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val challengeType = requireArguments()
            .getSerializable(ARGUMENT_CHALLENGE_TYPE) as ChallengeType?
        val user = requireArguments().getParcelable<User>(ARGUMENT_USER)

        viewModel.loadChallenges(challengeType, user)
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
