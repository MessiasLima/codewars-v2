package com.messiasjunior.codewarsv2.util

import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.AndroidInjector.Factory
import dagger.android.DispatchingAndroidInjector
import dagger.android.DispatchingAndroidInjector_Factory
import javax.inject.Provider

inline fun <reified T : Fragment> createFakeFragmentInjector(
    crossinline block: T.() -> Unit
): DispatchingAndroidInjector<Any> {
    val injector = AndroidInjector<Fragment> { instance ->
        if (instance is T) {
            instance.block()
        }
    }
    val factory = Factory<Fragment> { injector }
    val map = mapOf(
        Pair<Class<*>, Provider<Factory<*>>>(
            T::class.java,
            Provider { factory }
        )
    )
    return DispatchingAndroidInjector_Factory.newInstance(map, emptyMap())
}
