package com.gmail.l2t45s7e9.empty.di.ContactList;

import com.gmail.l2t45s7e9.empty.presentation.screens.ContactListFragment;
import dagger.Subcomponent;
import javax.inject.Singleton;
@Singleton
@Subcomponent(modules = ContactListModule.class)
public interface ContactListComponent {
    void inject(ContactListFragment contactListFragment);
}
