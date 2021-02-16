package com.gmail.l2t45s7e9.empty.di.App;

import android.content.ContentResolver;
import android.content.Context;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context context;
    private ContentResolver contentResolver;

    public AppModule(Context context, ContentResolver contentResolver) {
        this.context = context;
        this.contentResolver = contentResolver;
    }

    @Provides
    Context context() {
        return context;
    }

    @Provides
    ContentResolver contentResolver() {
        return contentResolver;
    }
}
