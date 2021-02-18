package com.gmail.l2t45s7e9.empty.di.ContactList;

import com.gmail.l2t45s7e9.empty.entity.Contact;
import java.util.List;

public interface ContactListRepository {
    List<Contact> loadShortInformation(String filterPattern);
}
