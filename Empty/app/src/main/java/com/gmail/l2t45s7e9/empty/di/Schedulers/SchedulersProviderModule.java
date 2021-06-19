package com.gmail.l2t45s7e9.empty.di.Schedulers;

import com.gmail.l2t45s7e9.library.interfaces.SchedulersProvider;
import com.gmail.l2t45s7e9.library.interfaces.SchedulersProviderModel;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
@Module
public class SchedulersProviderModule {
    @Provides
    @Singleton
    SchedulersProvider provideSchedulersProvider() {
        return new SchedulersProviderModel();
    }
}
