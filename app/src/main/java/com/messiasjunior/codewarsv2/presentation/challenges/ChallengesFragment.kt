package com.messiasjunior.codewarsv2.presentation.challenges

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.messiasjunior.codewarsv2.R
import com.messiasjunior.codewarsv2.databinding.FragmentChallengesBinding
import com.messiasjunior.codewarsv2.model.ChallengeType
import com.messiasjunior.codewarsv2.model.User
import com.messiasjunior.codewarsv2.presentation.user.UserFragmentDirections
import com.messiasjunior.codewarsv2.util.event.EventObserver
import dagger.android.support.AndroidSupportInjection
import java.io.IOException
import javax.inject.Inject

class ChallengesFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ChallengesViewModel.Factory
    private val viewModel by viewModels<ChallengesViewModel> { viewModelFactory }
    private lateinit var binding: FragmentChallengesBinding
    private lateinit var challengesAdapter: ChallengesAdapter
    private var user: User? = null
    private var challengeType: ChallengeType? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChallengesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        challengeType = requireArguments()
            .getSerializable(ARGUMENT_CHALLENGE_TYPE) as ChallengeType?
        user = requireArguments().getParcelable(ARGUMENT_USER)

        viewModel.loadChallenges(challengeType, user)

        setupChallengesRecyclerView()
        setupChallengeClickedEventHandler()
        setupErrorEventHandler()
    }

    private fun setupChallengesRecyclerView() {
        challengesAdapter = ChallengesAdapter(viewModel)

        with(binding.challengesRecyclerView) {
            adapter = challengesAdapter
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), VERTICAL))
        }

        viewModel.challenges.observe(viewLifecycleOwner, Observer(challengesAdapter::submitList))

        viewModel.reachedOnEndOfListEvent.observe(viewLifecycleOwner) {
            challengesAdapter.showEndOfListIndicator = it
        }
    }

    private fun setupChallengeClickedEventHandler() {
        viewModel.challengeClicked.observe(
            viewLifecycleOwner,
            EventObserver {
                val directions = UserFragmentDirections.showChallengeDetails(it)
                findNavController().navigate(directions)
            }
        )
    }

    private fun setupErrorEventHandler() {
        viewModel.onErrorEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                val message = when (it) {
                    is IOException -> getString(R.string.verify_network_connection)
                    else -> getString(R.string.generic_error_message)
                }

                Snackbar.make(requireView(), message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.try_again) {
                        viewModel.loadChallenges(challengeType, user, force = true)
                    }
                    .show()
            }
        )
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
