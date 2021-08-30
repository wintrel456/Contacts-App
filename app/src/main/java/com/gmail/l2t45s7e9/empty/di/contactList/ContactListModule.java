package com.gmail.l2t45s7e9.empty.di.contactList;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.gmail.l2t45s7e9.library.domain.ContactListViewModel;
import com.gmail.l2t45s7e9.library.domain.factories.ViewModelListFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ContactListModule {
    @Binds
    @IntoMap
    @ContactListViewModelKey(ContactListViewModel.class)
    abstract ViewModel bindContactListViewModel(ContactListViewModel contactListViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindContactListViewModelFactory(ViewModelListFactory viewModelListFactory);

}
