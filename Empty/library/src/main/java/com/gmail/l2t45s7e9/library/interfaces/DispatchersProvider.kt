package com.gmail.l2t45s7e9.library.interfaces

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named
import javax.inject.Qualifier

interface DispatchersProvider {
    fun io():CoroutineDispatcher
    fun ui():CoroutineDispatcher
    fun computation():CoroutineDispatcher
}