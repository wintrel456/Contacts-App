package com.gmail.l2t45s7e9.empty.di.App;

import android.app.Application;
public class AppDelegate extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDependencies();
    }

    private void initDependencies() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this, getContentResolver()))
                .build();
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            initDependencies();
        }
        return appComponent;
    }

}
