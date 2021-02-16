package com.gmail.l2t45s7e9.empty.di.App;

import android.content.ContentResolver;
import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class AppModule {
    private Context context;

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
    ContentResolver contentResolver() {
        return context.getContentResolver();
    }

}
