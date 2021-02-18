package com.gmail.l2t45s7e9.empty.di.ContactList;

import android.content.ContentResolver;
import android.content.Context;
import com.gmail.l2t45s7e9.empty.di.scopes.ContactListFragmentScope;
import com.gmail.l2t45s7e9.empty.repository.ContactListRepository;
import dagger.Module;
import dagger.Provides;
@Module
public class ContactListRepositoryModule {

    @Provides
    @ContactListFragmentScope
    ListRepositoryGetter listRepo(Context context, ContentResolver contentResolver) {
        return () -> new ContactListRepository(contentResolver, context);
    }
}
