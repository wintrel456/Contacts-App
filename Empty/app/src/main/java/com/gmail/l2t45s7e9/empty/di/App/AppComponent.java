package com.gmail.l2t45s7e9.empty.di.App;

import com.gmail.l2t45s7e9.empty.di.ContactDetails.ContactDetailsComponent;
import com.gmail.l2t45s7e9.empty.di.ContactList.ContactListComponent;
import com.gmail.l2t45s7e9.empty.di.Schedulers.SchedulersProviderModule;
import com.gmail.l2t45s7e9.library.interfaces.AppContainer;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {AppModule.class, SchedulersProviderModule.class})
public interface AppComponent extends AppContainer {
    ContactDetailsComponent plusContactDetailsContainer();

    ContactListComponent plusContactListContainer();

}
