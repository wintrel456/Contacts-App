package com.gmail.l2t45s7e9.library.repository;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import com.gmail.l2t45s7e9.java.entity.Contact;
import com.gmail.l2t45s7e9.java.interactor.ContactDetailsRepository;
import io.reactivex.rxjava3.core.Single;
import java.util.GregorianCalendar;

public class ContactDetailsRepositoryImpl implements ContactDetailsRepository {

    private final ContentResolver contentResolver;
    private final Context context;

    public ContactDetailsRepositoryImpl(Context context) {
        this.context = context;
        contentResolver = context.getContentResolver();
    }

    @Override
    public Single<Contact> loadDetailsInformation(String id, int color) {
        ContactsRepositoryDelegate contactsRepositoryDelegate = new ContactsRepositoryDelegate(context);
        Contact contact = null;
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(
                    ContactsContract.Contacts.CONTENT_URI,
                    null,
                    ContactsContract.Contacts._ID + "=" + id,
                    null,
                    ContactsContract.Contacts.DISPLAY_NAME);
            while (cursor.moveToNext()) {
                String name = contactsRepositoryDelegate.getName(cursor, id);
                String firstNumber = contactsRepositoryDelegate.getNumbers(cursor, id)[0];
                String secondNumber = contactsRepositoryDelegate.getNumbers(cursor, id)[1];
                String firstEmail = contactsRepositoryDelegate.getEmails(cursor, id)[0];
                String secondEmail = contactsRepositoryDelegate.getEmails(cursor, id)[1];
                String address = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                GregorianCalendar birthDate = contactsRepositoryDelegate.getBirthDate(cursor, id);
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
        Contact finalContact = contact;
        return Single.fromCallable(() -> finalContact);
    }


}
