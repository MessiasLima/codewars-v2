package com.messiasjunior.codewarsv2.util.resource

class Resource<out T> private constructor(
    val status: Status,
    val data: T? = null,
    val message: String? = null,
    val throwable: Throwable? = null,
    val endOfList: Boolean? = null,
    val shouldShowLoading: Boolean? = null
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        INFO
    }

    fun isLoading() = status == Status.LOADING
    fun isError() = status == Status.ERROR
    fun isSuccess() = status == Status.SUCCESS
    fun isInfo() = status == Status.INFO

    companion object {

        fun <T> success(data: T?, endOfList: Boolean? = null): Resource<T> {
            return Resource(
                status = Status.SUCCESS,
                data = data,
                endOfList = endOfList
            )
        }

        fun <T> error(msg: String? = null, data: T? = null, throwable: Throwable?): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                msg,
                throwable
            )
        }

        fun <T> loading(data: T? = null, shouldShowLoading: Boolean = true): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                shouldShowLoading = shouldShowLoading
            )
        }

        fun <T> info(data: T? = null, endOfList: Boolean? = null): Resource<T> {
            return Resource(
                Status.INFO,
                data,
                endOfList = endOfList
            )
        }
    }
}
