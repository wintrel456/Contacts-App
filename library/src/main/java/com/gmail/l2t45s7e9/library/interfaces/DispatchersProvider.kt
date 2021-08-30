package com.gmail.l2t45s7e9.library.interfaces

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersProvider {
    fun io(): CoroutineDispatcher
    fun ui(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
}
