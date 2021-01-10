package com.gmail.l2t45s7e9.empty.repository;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import com.gmail.l2t45s7e9.empty.domain.ContactDetailsViewModel;
import com.gmail.l2t45s7e9.empty.entity.Contact;
import java.lang.ref.WeakReference;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContactDetailsRepository {
    private final ContentResolver contentResolver;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public ContactDetailsRepository(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }


    public void getDetailsInformation(ContactDetailsViewModel.DetailsInformation callback, final String id, final int color) {
        final WeakReference<ContactDetailsViewModel.DetailsInformation> ref = new WeakReference<>(callback);
        try {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Contact result = loadDetailsInformation(id, color);
                    ContactDetailsViewModel.DetailsInformation local = ref.get();
                    if (local != null) {
                        local.getDetails(result);
                    }
                }
            }).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

    }

    private Contact loadDetailsInformation(String id, int color) {
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
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                String firstNumber = contactsRepositoryDelegate.getEmails()[0];
                String secondNumber = contactsRepositoryDelegate.getEmails()[1];
                String firstEmail = contactsRepositoryDelegate.getEmails()[0];
                String secondEmail = contactsRepositoryDelegate.getEmails()[1];
                String address = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                GregorianCalendar birthDate = contactsRepositoryDelegate.getBirthDate();
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
