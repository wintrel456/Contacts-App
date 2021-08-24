package com.gmail.l2t45s7e9.empty.di.Schedulers

import com.gmail.l2t45s7e9.library.interfaces.DispatcherProviderModel
import com.gmail.l2t45s7e9.library.interfaces.DispatchersProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DispatchersProviderModule {
    @Provides
    @Singleton
    fun provideDispatchersProvider(): DispatchersProvider {
        return DispatcherProviderModel()
    }
}