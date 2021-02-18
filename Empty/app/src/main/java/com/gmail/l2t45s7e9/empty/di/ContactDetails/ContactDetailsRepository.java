package com.gmail.l2t45s7e9.empty.di.ContactDetails;

import com.gmail.l2t45s7e9.empty.entity.Contact;
public interface ContactDetailsRepository {
    Contact loadDetailsInformation(String id, int color);
}
