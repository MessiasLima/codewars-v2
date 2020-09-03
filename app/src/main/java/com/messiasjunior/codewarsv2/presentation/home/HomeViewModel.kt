package com.messiasjunior.codewarsv2.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.messiasjunior.codewarsv2.model.User
import com.messiasjunior.codewarsv2.repository.UserRepository
import com.messiasjunior.codewarsv2.util.event.Event
import com.messiasjunior.codewarsv2.util.resource.Resource
import javax.inject.Inject

class HomeViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _searchUserEvent = MutableLiveData<String>()

    private val _userSearchResource = _searchUserEvent.switchMap { query ->
        liveData {
            emit(Resource.loading())
            emit(userRepository.searchUser(query))
        }
    }

    private val _userSelectedEvent = MediatorLiveData<Event<User>>().also { mediator ->
        mediator.addSource(_userSearchResource) { userResource ->
            if (userResource.isSuccess()) {
                mediator.value = Event(userResource.data!!)
            }
        }
    }
    val userSelectedEvent: LiveData<Event<User>> = _userSelectedEvent

    fun searchUser(query: String) {
        _searchUserEvent.value = query
    }

    class Factory @Inject constructor(
        private val userRepository: UserRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel(userRepository) as T
        }
    }
}
