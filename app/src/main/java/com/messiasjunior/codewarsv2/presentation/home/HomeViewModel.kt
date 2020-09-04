package com.messiasjunior.codewarsv2.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.map
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

    private val _sortOrder = MutableLiveData<UserRepository.SortOrder>()

    val savedUsersResource = _sortOrder.switchMap {
        liveData {
            emit(Resource.loading())
            emitSource(userRepository.findAll(it).map { Resource.success(it) })
        }
    }

    val isLoading: LiveData<Boolean> = MediatorLiveData<Boolean>().also { mediatorLiveData ->
        mediatorLiveData.addSource(_userSearchResource) { userResource ->
            mediatorLiveData.value = userResource.isLoading()
        }

        mediatorLiveData.addSource(savedUsersResource) { resource ->
            mediatorLiveData.value = resource.isLoading()
        }
    }

    val onErrorEvent: LiveData<Event<Throwable>> = MediatorLiveData<Event<Throwable>>().apply {
        addSource(_userSearchResource) {
            if (it.isError()) {
                value = Event(it.throwable!!)
            }
        }
    }

    init {
        _sortOrder.value = DEFAULT_SORT_ORDER
    }

    fun searchUser(query: String) {
        _searchUserEvent.value = query
    }

    fun selectUser(user: User) {
        _userSelectedEvent.value = Event(user)
    }

    fun setSortOrder(sortOrder: UserRepository.SortOrder) {
        _sortOrder.value = sortOrder
    }

    class Factory @Inject constructor(
        private val userRepository: UserRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel(userRepository) as T
        }
    }

    companion object {
        @JvmStatic
        private val DEFAULT_SORT_ORDER = UserRepository.SortOrder.SEARCH_DATE
    }
}
