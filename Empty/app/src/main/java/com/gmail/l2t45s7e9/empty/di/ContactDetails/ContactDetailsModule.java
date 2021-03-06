package com.gmail.l2t45s7e9.empty.di.ContactDetails;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.gmail.l2t45s7e9.library.domain.ContactDetailsViewModel;
import com.gmail.l2t45s7e9.library.domain.factories.ViewModelDetailsFactory;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
@Module
public abstract class ContactDetailsModule {
    @Binds
    @IntoMap
    @ContactDetailsViewModelKey(ContactDetailsViewModel.class)
    abstract ViewModel bindContactDetailsViewModel(ContactDetailsViewModel contactDetailsViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindContactDetailsViewModelFactory(ViewModelDetailsFactory viewModelDetailsFactory);
}
