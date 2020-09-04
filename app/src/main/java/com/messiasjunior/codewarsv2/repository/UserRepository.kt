package com.messiasjunior.codewarsv2.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.messiasjunior.codewarsv2.datasource.user.UserLocalDataSource
import com.messiasjunior.codewarsv2.datasource.user.UserRemoteDataSource
import com.messiasjunior.codewarsv2.model.Language
import com.messiasjunior.codewarsv2.model.User
import com.messiasjunior.codewarsv2.util.resource.Resource
import java.util.Date
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) {
    enum class SortOrder {
        HONOR, SEARCH_DATE
    }

    suspend fun searchUser(query: String): Resource<User> {
        return try {
            var user = userLocalDataSource.findByUsername(query)

            if (user == null) {
                user = userRemoteDataSource.searchUser(query)
                user.bestLanguage = getBestLanguage(user)
            }

            user.searchDate = Date().time

            userLocalDataSource.save(user)

            Resource.success(user)
        } catch (throwable: Throwable) {
            Resource.error(throwable = throwable)
        }
    }

    private fun getBestLanguage(user: User): Language? {
        val languages = user.ranks?.languages
        val entry = languages?.entries?.maxByOrNull { it.value.score }
        return if (entry != null) Language(entry.key, entry.value) else null
    }

    fun findAll(sortOrder: SortOrder): LiveData<PagedList<User>> {
        val pagedList = when (sortOrder) {
            SortOrder.HONOR -> userLocalDataSource.findOrderedByHonor()
            SortOrder.SEARCH_DATE -> userLocalDataSource.findOrderedBySearchDate()
        }

        return pagedList.toLiveData(DEFAULT_PAGE_SIZE)
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 5
    }
}
