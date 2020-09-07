package com.messiasjunior.codewarsv2.presentation.challengedetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.messiasjunior.codewarsv2.R
import com.messiasjunior.codewarsv2.databinding.FragmentChallengeDetailsBinding
import com.messiasjunior.codewarsv2.exception.ChallengeNotFoundException
import dagger.android.support.AndroidSupportInjection
import java.io.IOException
import javax.inject.Inject

class ChallengeDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ChallengeDetailsViewModel.Factory
    private val viewModel by viewModels<ChallengeDetailsViewModel> { viewModelFactory }
    private lateinit var binding: FragmentChallengeDetailsBinding
    private val args by navArgs<ChallengeDetailsFragmentArgs>()

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChallengeDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setChallenge(args.challenge)
        setupErrorHandling()
    }

    private fun setupErrorHandling() {
        viewModel.onError.observe(viewLifecycleOwner) {
            val message = when (it) {
                is ChallengeNotFoundException -> R.string.challenge_not_found
                is IOException -> R.string.verify_network_connection
                else -> R.string.generic_error_message
            }

            Snackbar.make(requireView(), message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.try_again) { viewModel.setChallenge(args.challenge, true) }
                .show()
        }
    }
}
