package com.gmail.l2t45s7e9.empty.di.ContactList;

import com.gmail.l2t45s7e9.empty.di.App.AppDelegate;
import com.gmail.l2t45s7e9.empty.domain.factories.ViewModelListFactory;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class ContactListModule {
    @Provides
    @Singleton
    ViewModelListFactory provideViewModelListFactory(AppDelegate appDelegate) {
        return new ViewModelListFactory(appDelegate);
    }
}
