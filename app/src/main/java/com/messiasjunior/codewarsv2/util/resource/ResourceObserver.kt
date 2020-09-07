package com.messiasjunior.codewarsv2.util.resource

import androidx.lifecycle.Observer

class ResourceObserver<T>(private val callback: (T) -> Unit) : Observer<Resource<T>> {
    override fun onChanged(t: Resource<T>?) {
        if (t?.isSuccess() == true) {
            t.data?.let(callback::invoke)
        }
    }
}
