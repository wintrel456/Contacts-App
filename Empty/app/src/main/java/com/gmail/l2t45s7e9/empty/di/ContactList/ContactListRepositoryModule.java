package com.gmail.l2t45s7e9.empty.di.ContactList;

import android.content.ContentResolver;
import android.content.Context;
import com.gmail.l2t45s7e9.empty.di.scopes.ContactListFragmentScope;
import com.gmail.l2t45s7e9.java.interactor.ContactListInteractor;
import com.gmail.l2t45s7e9.java.interactor.ContactListModel;
import com.gmail.l2t45s7e9.java.interactor.ContactListRepository;
import com.gmail.l2t45s7e9.library.repository.ContactListRepositoryImpl;
import dagger.Module;
import dagger.Provides;
@Module
public class ContactListRepositoryModule {

    @Provides
    @ContactListFragmentScope
    ContactListRepository provideContactListRepository(ContentResolver contentResolver, Context context) {
        return new ContactListRepositoryImpl(contentResolver, context);
    }

    @Provides
    @ContactListFragmentScope
    ContactListInteractor provideContactInteractor(ContactListRepository contactListRepository) {
        return new ContactListModel(contactListRepository);
    }
}
