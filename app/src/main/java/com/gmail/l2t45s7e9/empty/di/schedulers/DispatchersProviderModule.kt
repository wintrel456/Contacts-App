package com.gmail.l2t45s7e9.empty.di.schedulers

import com.gmail.l2t45s7e9.library.interfaces.DispatcherProviderModel
import com.gmail.l2t45s7e9.library.interfaces.DispatchersProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DispatchersProviderModule {
    @Singleton
    @Provides
    fun provideDispatchersProvider(): DispatchersProvider {
        return DispatcherProviderModel()
    }
}
