package com.gmail.l2t45s7e9.empty.di.ContactDetails;

import android.content.ContentResolver;
import com.gmail.l2t45s7e9.empty.di.scopes.ContactDetailsFragmentScope;
import com.gmail.l2t45s7e9.empty.repository.ContactDetailsRepository;
import dagger.Module;
import dagger.Provides;
@Module
public class ContactDetailsRepositoryModule {

    @Provides
    @ContactDetailsFragmentScope
    DetailsRepositoryGetter provideDetailsRepositoryGetter(ContentResolver contentResolver) {
        return () -> new ContactDetailsRepository(contentResolver);
    }

}
