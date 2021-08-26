package com.gmail.l2t45s7e9.empty.di.Schedulers

import com.gmail.l2t45s7e9.library.interfaces.*
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class DispatchersProviderModule {
    @Singleton
    @Provides
    fun provideDispatchersProvider(): DispatchersProvider {
        return DispatcherProviderModel()
    }
}