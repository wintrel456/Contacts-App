package com.gmail.l2t45s7e9.empty.di.contactDetails;

import com.gmail.l2t45s7e9.empty.di.scopes.ContactDetailsFragmentScope;
import com.gmail.l2t45s7e9.library.interfaces.ContactDetailsContainer;

import dagger.Subcomponent;

@ContactDetailsFragmentScope
@Subcomponent(modules = {ContactDetailsModule.class, ContactDetailsRepositoryModule.class})
public interface ContactDetailsComponent extends ContactDetailsContainer {
}
