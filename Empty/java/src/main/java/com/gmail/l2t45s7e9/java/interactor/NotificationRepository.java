package com.gmail.l2t45s7e9.java.interactor;

import com.gmail.l2t45s7e9.java.entity.Contact;
import java.util.GregorianCalendar;
public interface NotificationRepository {
    void setManager(Contact contact, GregorianCalendar calendar);

    void cancelManager(Contact contact);

    boolean status(String position);
}
