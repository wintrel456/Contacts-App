package com.gmail.l2t45s7e9.empty.di.contactList;

import android.content.Context;

import com.gmail.l2t45s7e9.empty.di.scopes.ContactListFragmentScope;
import com.gmail.l2t45s7e9.java.interactor.ContactListInteractor;
import com.gmail.l2t45s7e9.java.interactor.ContactListModel;
import com.gmail.l2t45s7e9.java.interactor.ContactListRepository;
import com.gmail.l2t45s7e9.library.interfaces.DispatchersProvider;
import com.gmail.l2t45s7e9.library.repository.ContactListRepositoryImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactListRepositoryModule {

    @Provides
    @ContactListFragmentScope
    ContactListRepository provideContactListRepository(Context context, DispatchersProvider dispatchersProvider) {
        return new ContactListRepositoryImpl(context, dispatchersProvider);
    }

    @Provides
    @ContactListFragmentScope
    ContactListInteractor provideContactInteractor(ContactListRepository contactListRepository) {
        return new ContactListModel(contactListRepository);
    }
}
