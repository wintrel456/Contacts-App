package com.gmail.l2t45s7e9.empty.di.App;

import android.app.Application;

import com.gmail.l2t45s7e9.library.interfaces.AppContainer;
import com.gmail.l2t45s7e9.library.interfaces.HasAppContainer;

public class AppDelegate extends Application implements HasAppContainer {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDependencies();
    }

    private void initDependencies() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    @Override
    public AppContainer appContainer() {
        if (appComponent == null) {
            initDependencies();
        }
        return appComponent;
    }
}
