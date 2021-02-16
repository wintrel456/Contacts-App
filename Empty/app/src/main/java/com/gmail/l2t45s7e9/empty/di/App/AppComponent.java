package com.gmail.l2t45s7e9.empty.di.App;

import com.gmail.l2t45s7e9.empty.di.ContactDetails.ContactDetailsComponent;
import dagger.Component;

@Component(modules = {AppModule.class})
public interface AppComponent {
    ContactDetailsComponent plusContactDetailsComponent();
}
