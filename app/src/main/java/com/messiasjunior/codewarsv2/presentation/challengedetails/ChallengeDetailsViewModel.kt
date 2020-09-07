package com.messiasjunior.codewarsv2.presentation.challengedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.messiasjunior.codewarsv2.model.Challenge
import com.messiasjunior.codewarsv2.repository.ChallengeRepository
import com.messiasjunior.codewarsv2.util.resource.Resource
import javax.inject.Inject

class ChallengeDetailsViewModel(
    private val challengeRepository: ChallengeRepository
) : ViewModel() {
    private val _challenge = MutableLiveData<Challenge>()
    private val _challengeDetailsResource: LiveData<Resource<Challenge>> = _challenge.switchMap {
        liveData {
            emit(Resource.loading())
            emit(challengeRepository.getChallengeDetails(it.id))
        }
    }

    val challengeDetails: LiveData<Challenge> = MediatorLiveData<Challenge>().apply {
        addSource(_challengeDetailsResource) {
            if (it.isSuccess()) {
                value = it.data
            }
        }
    }

    val isLoading = _challengeDetailsResource.map { it.isLoading() }

    fun setChallenge(challenge: Challenge) {
        _challenge.value = challenge
    }

    class Factory @Inject constructor(
        private val challengeRepository: ChallengeRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ChallengeDetailsViewModel(challengeRepository) as T
        }
    }
}
