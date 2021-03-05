package com.gmail.l2t45s7e9.empty.di.ContactDetails;

import android.content.ContentResolver;
import com.gmail.l2t45s7e9.empty.di.scopes.ContactDetailsFragmentScope;
import com.gmail.l2t45s7e9.java.interactor.ContactDetailsInteractor;
import com.gmail.l2t45s7e9.java.interactor.ContactDetailsRepository;
import com.gmail.l2t45s7e9.java.interactor.ContactDetilsModel;
import com.gmail.l2t45s7e9.library.repository.ContactDetailsRepositoryImpl;
import dagger.Module;
import dagger.Provides;
@Module
public class ContactDetailsRepositoryModule {

    @Provides
    @ContactDetailsFragmentScope
    ContactDetailsRepository provideContactDetailsRepository(ContentResolver contentResolver) {
        return new ContactDetailsRepositoryImpl(contentResolver);
    }

    @Provides
    @ContactDetailsFragmentScope
    ContactDetailsInteractor provideContactDetailsInteractor(ContactDetailsRepository contactDetailsRepository) {
        return new ContactDetilsModel(contactDetailsRepository);
    }

}
