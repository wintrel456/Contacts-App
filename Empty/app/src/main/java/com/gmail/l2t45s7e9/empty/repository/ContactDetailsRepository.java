package com.gmail.l2t45s7e9.empty.repository;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import com.gmail.l2t45s7e9.empty.entity.Contact;
import java.util.GregorianCalendar;

public class ContactDetailsRepository {

    private final ContentResolver contentResolver;

    public ContactDetailsRepository(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public Contact loadDetailsInformation(String id, int color) {
        ContactsRepositoryDelegate contactsRepositoryDelegate = new ContactsRepositoryDelegate(contentResolver, id);
        Contact contact = null;
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone._ID + "=" + id,
                    null,
                    ContactsContract.Contacts.DISPLAY_NAME);
            while (cursor.moveToNext()) {
                String name = contactsRepositoryDelegate.getName(cursor);
                String firstNumber = contactsRepositoryDelegate.getNumbers(cursor)[0];
                String secondNumber = contactsRepositoryDelegate.getNumbers(cursor)[1];
                String firstEmail = contactsRepositoryDelegate.getEmails(cursor)[0];
                String secondEmail = contactsRepositoryDelegate.getEmails(cursor)[1];
                String address = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                GregorianCalendar birthDate = contactsRepositoryDelegate.getBirthDate(cursor);
                contact = new Contact(id,
                        name,
                        firstNumber,
                        secondNumber,
                        firstEmail,
                        secondEmail,
                        address,
                        birthDate,
                        color);
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return contact;
    }
}
