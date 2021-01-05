package com.gmail.l2t45s7e9.empty.domain;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gmail.l2t45s7e9.empty.repository.Repository;
import com.gmail.l2t45s7e9.empty.repository.entity.Contact;

public class ContactDetailsViewModel extends AndroidViewModel {
    public interface DetailsInformation {
        void getDetails(Contact contact);
    }

    private Repository repository;
    private MutableLiveData<Contact> contactDetailsMutableLiveData;
    private String id;
    private int color;

    public ContactDetailsViewModel(@NonNull Application application, String id, int color) {
        super(application);
        repository = new Repository(application.getContentResolver(), application.getApplicationContext());
        this.id = id;
        this.color = color;
    }

    public LiveData<Contact> getContactDetails() {
        if (contactDetailsMutableLiveData == null) {
            contactDetailsMutableLiveData = new MutableLiveData<Contact>();
            loadContactDetails();
        }
        return contactDetailsMutableLiveData;
    }

    private void loadContactDetails() {
        DetailsInformation callback = new DetailsInformation() {
            @Override
            public void getDetails(Contact contact) {
                contactDetailsMutableLiveData.postValue(contact);
            }
        };
        repository.getDetailsInformation(callback, id, color);
    }
}
