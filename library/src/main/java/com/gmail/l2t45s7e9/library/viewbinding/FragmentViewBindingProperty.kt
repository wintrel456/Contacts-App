package com.gmail.l2t45s7e9.library.viewbinding

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

@MainThread
inline fun <reified T : ViewBinding> Fragment.viewBinding(): FragmentViewBindingDelegate<T> =
    FragmentViewBindingDelegate(
        fragment = this,
        viewBindingClazz = T::class.java
    )
