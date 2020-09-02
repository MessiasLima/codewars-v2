package com.messiasjunior.codewarsv2.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.messiasjunior.codewarsv2.repository.UserRepository
import javax.inject.Inject

class HomeViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    val appName = "Codewars v2"

    class Factory @Inject constructor(
        private val userRepository: UserRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HomeViewModel(userRepository) as T
        }
    }
}
