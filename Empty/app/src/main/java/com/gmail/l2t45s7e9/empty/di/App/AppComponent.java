package com.gmail.l2t45s7e9.empty.di.App;

import com.gmail.l2t45s7e9.empty.di.ContactDetails.ContactDetailsComponent;
import com.gmail.l2t45s7e9.empty.di.ContactList.ContactListComponent;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    ContactDetailsComponent plusContactDetailsComponent();

    ContactListComponent plusContactListComponent();
}
