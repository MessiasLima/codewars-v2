package com.messiasjunior.codewarsv2.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.messiasjunior.codewarsv2.util.resource.Resource

fun <T> getPagedListFrom(list: List<T>): LiveData<PagedList<T>> {
    return object : DataSource.Factory<Int, T>() {
        override fun create(): DataSource<Int, T> {
            return object : PageKeyedDataSource<Int, T>() {
                override fun loadInitial(
                    params: LoadInitialParams<Int>,
                    callback: LoadInitialCallback<Int, T>
                ) {
                    callback.onResult(list, null, null)
                }

                override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
                    callback.onResult(list, null)
                }

                override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
                    callback.onResult(list, null)
                }
            }
        }
    }.toLiveData(pageSize = list.size + 1)
}

fun <T> getResourcePagedListFrom(list: List<T>): LiveData<Resource<PagedList<T>>> {
    return getPagedListFrom(list).map { Resource.success(it) }
}
