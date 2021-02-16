package com.gmail.l2t45s7e9.empty.di.ContactDetails;

import com.gmail.l2t45s7e9.empty.di.scopes.ContactDetailsFragmentScope;
import com.gmail.l2t45s7e9.empty.presentation.screens.ContactDetailsFragment;
import dagger.Subcomponent;
@ContactDetailsFragmentScope
@Subcomponent(modules = {ContactDetailsModule.class, ContactDetailsRepositoryModule.class})
public interface ContactDetailsComponent {
    void inject(ContactDetailsFragment contactDetailsFragment);
}
