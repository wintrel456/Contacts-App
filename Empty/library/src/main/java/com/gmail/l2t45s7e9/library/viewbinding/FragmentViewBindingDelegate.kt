package com.gmail.l2t45s7e9.library.viewbinding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewBindingDelegate<T : ViewBinding>(
    private val fragment: Fragment,
    viewBindingBind: ((View) -> T)? = null,
    viewBindingClazz: Class<T>? = null
) : ReadOnlyProperty<Fragment, T> {

    private var binding: T? = null
    private val bind: (View) -> T

    init {
        require(viewBindingBind != null || viewBindingClazz != null) {
            "Both viewBindingBind and viewBindingClazz are null. Please provide at least one."
        }

        bind = viewBindingBind ?: run {
            val method by lazy(LazyThreadSafetyMode.NONE) {
                viewBindingClazz?.getMethod(
                    "bind",
                    View::class.java
                )
            }

            @Suppress("UNCHECKED_CAST")
            fun(view: View): T = method?.invoke(null, view) as T
        }
        fragment.lifecycle.addObserver(FragmentLifecycleObserver())
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        binding?.let { return it }
        check(fragment.viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            "Attempt to get view binding when fragment view is destroyed"
        }
        return bind(thisRef.requireView()).also { binding = it }
    }

    private inner class FragmentLifecycleObserver : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            fragment.viewLifecycleOwnerLiveData.observe(
                fragment,
                Observer { viewLifecycleOwner: LifecycleOwner? ->
                    viewLifecycleOwner ?: return@Observer

                    val viewLifecycleObserver = object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            viewLifecycleOwner.lifecycle.removeObserver(this)
                            binding = null
                        }
                    }

                    viewLifecycleOwner.lifecycle.addObserver(viewLifecycleObserver)
                }
            )
        }

        override fun onDestroy(owner: LifecycleOwner) {
            fragment.lifecycle.removeObserver(this)
            binding = null
        }
    }
}