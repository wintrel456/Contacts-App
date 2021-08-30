package com.gmail.l2t45s7e9.empty.di.App;

import android.content.Context;

import com.gmail.l2t45s7e9.java.interactor.CurrentDate;
import com.gmail.l2t45s7e9.java.interactor.DateModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context context() {
        return context;
    }

    @Provides
    @Singleton
    CurrentDate currentDate() {
        return new DateModel();
    }

}
