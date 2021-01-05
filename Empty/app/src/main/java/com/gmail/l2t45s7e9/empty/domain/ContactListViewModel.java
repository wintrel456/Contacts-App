package com.gmail.l2t45s7e9.empty.domain;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gmail.l2t45s7e9.empty.repository.Repository;
import com.gmail.l2t45s7e9.empty.repository.entity.Contact;

import java.util.List;

public class ContactListViewModel extends AndroidViewModel {
    public interface ShortInformation {
        void getContactList(List<Contact> result);
    }

    private Repository repository;
    private MutableLiveData<List<Contact>> contactListMutableLiveData;


    public ContactListViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application.getContentResolver(), application.getApplicationContext());
    }

    public LiveData<List<Contact>> getContactsList() {
        if (contactListMutableLiveData == null) {
            contactListMutableLiveData = new MutableLiveData<List<Contact>>();
            loadContactList();
        }
        return contactListMutableLiveData;
    }


    private void loadContactList() {
        ShortInformation callback = new ShortInformation() {
            @Override
            public void getContactList(List<Contact> result) {
                contactListMutableLiveData.postValue(result);
            }
        };
        repository.getShortInformation(callback);
    }


}
