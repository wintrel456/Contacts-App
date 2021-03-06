package com.gmail.l2t45s7e9.java.interactor;

import com.gmail.l2t45s7e9.java.entity.Contact;
import io.reactivex.rxjava3.core.Single;
public class ContactDetilsModel implements ContactDetailsInteractor {

    private ContactDetailsRepository contactDetailsRepository;

    public ContactDetilsModel(ContactDetailsRepository contactDetailsRepository) {
        this.contactDetailsRepository = contactDetailsRepository;
    }

    @Override
    public Single<Contact> getContactDetailsRepo(String id, int color) {
        return contactDetailsRepository.loadDetailsInformation(id, color);
    }
}
