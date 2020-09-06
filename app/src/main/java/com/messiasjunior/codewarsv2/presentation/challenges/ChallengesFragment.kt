package com.messiasjunior.codewarsv2.presentation.challenges

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.messiasjunior.codewarsv2.R
import com.messiasjunior.codewarsv2.databinding.FragmentChallengesBinding
import com.messiasjunior.codewarsv2.model.ChallengeType
import com.messiasjunior.codewarsv2.model.User
import com.messiasjunior.codewarsv2.presentation.user.UserFragmentDirections
import com.messiasjunior.codewarsv2.util.event.EventObserver
import com.messiasjunior.codewarsv2.util.resource.ResourceObserver
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ChallengesFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ChallengesViewModel.Factory
    private val viewModel by viewModels<ChallengesViewModel> { viewModelFactory }
    private lateinit var binding: FragmentChallengesBinding

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

        val challengeType = requireArguments()
            .getSerializable(ARGUMENT_CHALLENGE_TYPE) as ChallengeType?
        val user = requireArguments().getParcelable<User>(ARGUMENT_USER)

        viewModel.loadChallenges(challengeType, user)

        setupChallengesRecyclerView()
        setupEndOfListEventHandler()
        setupChallengeClickedEventHandler()
    }

    private fun setupChallengesRecyclerView() {
        val adapter = ChallengesAdapter(viewModel)
        with(binding.challengesRecyclerView) {
            setAdapter(adapter)
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        viewModel.challenges.observe(viewLifecycleOwner, ResourceObserver(adapter::submitList))
    }

    private fun setupEndOfListEventHandler() {
        viewModel.reachedOnEndOfListEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                if (it) {
                    Snackbar.make(
                        requireView(),
                        R.string.all_data_were_fetched,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        )
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
