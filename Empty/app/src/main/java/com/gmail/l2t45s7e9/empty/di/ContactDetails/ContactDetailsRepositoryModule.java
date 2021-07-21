package com.gmail.l2t45s7e9.empty.di.ContactDetails;

import android.content.Context;
import com.gmail.l2t45s7e9.empty.di.scopes.ContactDetailsFragmentScope;
import com.gmail.l2t45s7e9.java.entity.BirthDateNotificationModel;
import com.gmail.l2t45s7e9.java.interactor.ContactDetailsInteractor;
import com.gmail.l2t45s7e9.java.interactor.ContactDetailsRepository;
import com.gmail.l2t45s7e9.java.interactor.ContactDetilsModel;
import com.gmail.l2t45s7e9.java.interactor.CurrentDate;
import com.gmail.l2t45s7e9.java.interactor.NotificationInteractor;
import com.gmail.l2t45s7e9.java.interactor.NotificationRepository;
import com.gmail.l2t45s7e9.library.dataBase.ContactAddressDataBase;
import com.gmail.l2t45s7e9.library.repository.ContactDetailsRepositoryImpl;
import com.gmail.l2t45s7e9.library.repository.NotificationRepositoryImpl;
import dagger.Module;
import dagger.Provides;
@Module
public class ContactDetailsRepositoryModule {

    @Provides
    @ContactDetailsFragmentScope
    ContactDetailsRepository provideContactDetailsRepository(
            Context context,
            ContactAddressDataBase contactAddressDataBase
    ) {
        return new ContactDetailsRepositoryImpl(context, contactAddressDataBase);
    }

    @Provides
    @ContactDetailsFragmentScope
    ContactDetailsInteractor provideContactDetailsInteractor(ContactDetailsRepository contactDetailsRepository) {
        return new ContactDetilsModel(contactDetailsRepository);
    }

    @Provides
    @ContactDetailsFragmentScope
    NotificationRepository provideNotificationRepository(Context context) {
        return new NotificationRepositoryImpl(context);
    }


    @Provides
    @ContactDetailsFragmentScope
    NotificationInteractor provideNotificationInteractor(CurrentDate currentDate, NotificationRepository notificationRepository) {
        return new BirthDateNotificationModel(currentDate, notificationRepository);
    }

}
