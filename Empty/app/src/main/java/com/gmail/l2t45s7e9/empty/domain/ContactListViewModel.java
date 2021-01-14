package com.gmail.l2t45s7e9.empty.domain;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gmail.l2t45s7e9.empty.entity.Contact;
import com.gmail.l2t45s7e9.empty.repository.ContactListRepository;

import java.util.List;

public class ContactListViewModel extends AndroidViewModel {
    public interface ShortInformation {
        void getContactList(List<Contact> result);
    }

    private ContactListRepository contactListRepository;
    private MutableLiveData<List<Contact>> contactListMutableLiveData = new MutableLiveData<>();
    public LiveData<List<Contact>> listLiveData;

    public ContactListViewModel(@NonNull Application application) {
        super(application);
        contactListRepository = new ContactListRepository(application.getContentResolver(), application.getApplicationContext());
        listLiveData = loadContactList();
    }

    private LiveData<List<Contact>> loadContactList() {
        ShortInformation callback = new ShortInformation() {
            @Override
            public void getContactList(List<Contact> result) {
                contactListMutableLiveData.postValue(result);
            }
        };
        contactListRepository.getShortInformation(callback);
        return contactListMutableLiveData;
    }
}
