package com.messiasjunior.codewarsv2.presentation.challengedetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.messiasjunior.codewarsv2.databinding.FragmentChallengeDetailsBinding
import dagger.android.support.AndroidSupportInjection
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
    }
}
