package com.gmail.l2t45s7e9.java.interactor;

import com.gmail.l2t45s7e9.java.entity.Contact;
import io.reactivex.rxjava3.core.Single;
public interface ContactDetailsInteractor {
    Single<Contact> getContactDetailsRepo(String id, int color);
}
