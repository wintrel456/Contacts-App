package com.gmail.l2t45s7e9.empty.di.schedulers;

import com.gmail.l2t45s7e9.library.interfaces.SchedulersProvider;
import com.gmail.l2t45s7e9.library.interfaces.SchedulersProviderModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SchedulersProviderModule {

    @Provides
    @Singleton
    SchedulersProvider provideSchedulersProvider() {
        return new SchedulersProviderModel();
    }
}
