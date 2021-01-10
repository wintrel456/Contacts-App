package com.gmail.l2t45s7e9.empty.domain;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gmail.l2t45s7e9.empty.entity.Contact;
import com.gmail.l2t45s7e9.empty.repository.ContactDetailsRepository;

public class ContactDetailsViewModel extends AndroidViewModel {
    public interface DetailsInformation {
        void getDetails(Contact contact);
    }

    private final ContactDetailsRepository contactDetailsRepository;
    private MutableLiveData<Contact> contactDetailsMutableLiveData = new MutableLiveData<>();
    public LiveData<Contact> contactLiveData;
    private String id;
    private int color;

    public ContactDetailsViewModel(@NonNull Application application, String id, int color) {
        super(application);
        contactDetailsRepository = new ContactDetailsRepository(application.getContentResolver());
        this.id = id;
        this.color = color;
        contactLiveData = loadContactDetails();
    }

    private LiveData<Contact> loadContactDetails() {
        DetailsInformation callback = new DetailsInformation() {
            @Override
            public void getDetails(Contact contact) {
                contactDetailsMutableLiveData.postValue(contact);
            }
        };
        contactDetailsRepository.getDetailsInformation(callback, id, color);
        return contactDetailsMutableLiveData;
    }
}
