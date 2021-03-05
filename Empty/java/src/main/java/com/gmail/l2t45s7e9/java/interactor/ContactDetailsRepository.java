package com.gmail.l2t45s7e9.java.interactor;

import com.gmail.l2t45s7e9.java.entity.Contact;
import io.reactivex.rxjava3.core.Single;
public interface ContactDetailsRepository {
    Single<Contact> loadDetailsInformation(String id, int color);
}
