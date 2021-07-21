package com.gmail.l2t45s7e9.library.repository;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import com.gmail.l2t45s7e9.java.entity.Contact;
import com.gmail.l2t45s7e9.java.interactor.ContactDetailsRepository;
import com.gmail.l2t45s7e9.library.dataBase.ContactAddressDataBase;
import io.reactivex.rxjava3.core.Single;
import java.util.GregorianCalendar;

public class ContactDetailsRepositoryImpl implements ContactDetailsRepository {

    private final ContentResolver contentResolver;
    private final Context context;
    private ContactAddressDataBase db;

    public ContactDetailsRepositoryImpl(Context context, ContactAddressDataBase contactAddressDataBase) {
        this.context = context;
        contentResolver = context.getContentResolver();
        db = contactAddressDataBase;
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
                GregorianCalendar birthDate = contactsRepositoryDelegate.getBirthDate(cursor, id);
                contact = new Contact(id,
                        name,
                        firstNumber,
                        secondNumber,
                        firstEmail,
                        secondEmail,
                        "",
                        birthDate,
                        color);
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        Contact finalContact = contact;
        return Single.fromCallable(() -> finalContact)
                .zipWith(getAddress(id), (contact1, address) -> new Contact(
                        contact1.getId(),
                        contact1.getName(),
                        contact1.getFirstNumber(),
                        contact1.getSecondNumber(),
                        contact1.getFirstEmail(),
                        contact1.getSecondEmail(),
                        address,
                        contact1.getBirthDate(),
                        contact1.getContactColor()
                ));
    }

    public Single<String> getAddress(String id) {
        return db.contactDao().loadContactById(id).onErrorReturnItem("");
    }
}
