package com.messiasjunior.codewarsv2.presentation.challenges

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.messiasjunior.codewarsv2.model.Challenge
import com.messiasjunior.codewarsv2.model.ChallengeType
import com.messiasjunior.codewarsv2.model.User
import com.messiasjunior.codewarsv2.repository.ChallengeRepository
import com.messiasjunior.codewarsv2.util.event.Event
import javax.inject.Inject

class ChallengesViewModel(
    private val challengeRepository: ChallengeRepository
) : ViewModel() {
    private val _loadChallengesEvent = MutableLiveData<Pair<User, ChallengeType>>()

    val challenges = _loadChallengesEvent.switchMap {
        challengeRepository.findChallenges(it.first, it.second)
    }

    val isLoading = challenges.map {
        it.isLoading() && it.shouldShowLoading == true
    }

    val isEmpty: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(challenges) {
            if (it.isSuccess()) {
                value = it.data?.isEmpty() == true
            }
        }
    }

    val reachedOnEndOfListEvent: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(challenges) {
            if (it.isSuccess()) {
                value = it.endOfList
            }
        }
    }

    private val _onErrorEvent = MediatorLiveData<Event<Throwable>>().apply {
        addSource(challenges) {
            if (it.isError()) {
                value = Event(it.throwable!!)
            }
        }
    }
    val onErrorEvent: LiveData<Event<Throwable>> = _onErrorEvent

    private val _challengeClickedEvent = MutableLiveData<Challenge>()
    val challengeClicked: LiveData<Event<Challenge>> = _challengeClickedEvent.map { Event(it) }

    fun loadChallenges(challengeType: ChallengeType?, user: User?, force: Boolean = false) {
        if (_loadChallengesEvent.value != null && !force) return // To avoid duplicate calls

        if (challengeType != null && user != null) {
            _loadChallengesEvent.value = Pair(user, challengeType)
        } else {
            _onErrorEvent.value = Event(
                IllegalArgumentException("Neither user or challenge type can be null")
            )
        }
    }

    fun onChallengeClicked(challenge: Challenge) {
        _challengeClickedEvent.value = challenge
    }

    class Factory @Inject constructor(
        private val challengeRepository: ChallengeRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ChallengesViewModel(challengeRepository) as T
        }
    }
}
