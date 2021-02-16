package com.gmail.l2t45s7e9.empty.di.ContactDetails;

import com.gmail.l2t45s7e9.empty.presentation.screens.ContactDetailsFragment;
import dagger.Subcomponent;

@Subcomponent(modules = {ContactDetailsModule.class, CDModule2.class})
public interface ContactDetailsComponent {
    void inject(ContactDetailsFragment contactDetailsFragment);
}
