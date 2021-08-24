package com.gmail.l2t45s7e9.java.interactor;

import com.gmail.l2t45s7e9.java.entity.Contact;
import io.reactivex.rxjava3.core.Single;
import java.util.List;
public class ContactListModel implements ContactListInteractor {

    private ContactListRepository contactListRepository;

    public ContactListModel(ContactListRepository contactListRepository) {
        this.contactListRepository = contactListRepository;
    }

    @Override
    public Single<List<Contact>> getContactListRepo(String filterPattern) {
        return contactListRepository.loadShortInformation(filterPattern);
    }

}
