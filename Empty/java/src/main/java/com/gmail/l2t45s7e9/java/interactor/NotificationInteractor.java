package com.gmail.l2t45s7e9.java.interactor;

import com.gmail.l2t45s7e9.java.entity.Contact;

public interface NotificationInteractor {
    void setNotification(Contact contact);

    void cancelNotification(Contact contact);

    boolean status(Contact contact);
}
