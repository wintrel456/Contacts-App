package com.gmail.l2t45s7e9.empty.di.ContactList;

import com.gmail.l2t45s7e9.empty.di.scopes.ContactListFragmentScope;
import com.gmail.l2t45s7e9.library.interfaces.ContactListContainer;
import dagger.Subcomponent;
@ContactListFragmentScope
@Subcomponent(modules = {ContactListModule.class, ContactListRepositoryModule.class})
public interface ContactListComponent extends ContactListContainer {
}
