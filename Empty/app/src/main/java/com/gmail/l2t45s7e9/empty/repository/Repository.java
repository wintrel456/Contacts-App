package com.gmail.l2t45s7e9.empty.repository;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.gmail.l2t45s7e9.empty.R;
import com.gmail.l2t45s7e9.empty.domain.ContactDetailsViewModel;
import com.gmail.l2t45s7e9.empty.domain.ContactListViewModel;
import com.gmail.l2t45s7e9.empty.repository.entity.Contact;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private ContentResolver contentResolver;
    private Context context;

    public Repository(ContentResolver contentResolver, Context context) {
        this.contentResolver = contentResolver;
        this.context = context;
    }

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public void getShortInformation(ContactListViewModel.ShortInformation callback) {
        final WeakReference<ContactListViewModel.ShortInformation> ref = new WeakReference<>(callback);
        try {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    List<Contact> result = loadShortInformation();
                    ContactListViewModel.ShortInformation local = ref.get();
                    if (local != null) {
                        local.getContactList(result);
                    }
                }
            }).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
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
        }
        executorService.shutdown();
    }

    private String[] getNumbers(String id) {
        String[] number = new String[2];
        int count = 0;
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone._ID + "=" + id,
                    null,
                    null);
            while (cursor.moveToNext()) {
                if (count < 2) {
                    if (count == 0) {
                        if (cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER)) != null) {
                            number[count] = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER));
                        } else {
                            number[count] = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
                        }

                    } else {
                        number[count] = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
                    }
                } else {
                    break;
                }
                count++;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return number;
    }

    private String[] getEmails(String id) {
        String[] email = new String[2];
        int count = 0;
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone._ID + "=" + id,
                    null,
                    null);
            while (cursor.moveToNext()) {
                if (count < 2) {
                    email[count] = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                } else {
                    break;
                }
                count++;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return email;
    }

    private List<Contact> loadShortInformation() {
        List<Contact> contacts = new ArrayList<>();
        Set<String> set = new HashSet<>();
        Random random = new Random();
        int[] colors = context.getResources().getIntArray(R.array.colors_list);
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.Contacts.HAS_PHONE_NUMBER,
                    null,
                    ContactsContract.Contacts.DISPLAY_NAME);
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                String firstNumber = getNumbers(id)[0];
                Contact contact = new Contact(id,
                        name,
                        firstNumber,
                        colors[random.nextInt(colors.length)]);

                if (!set.contains(firstNumber)) {
                    contacts.add(contact);
                    set.add(firstNumber);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return contacts;
    }

    private Contact loadDetailsInformation(String id, int color) {
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
                String firstNumber = getNumbers(id)[0];
                String secondNumber = getNumbers(id)[1];
                String firstEmail = getEmails(id)[0];
                String secondEmail = getEmails(id)[1];
                String address = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                contact = new Contact(id,
                        name,
                        firstNumber,
                        secondNumber,
                        firstEmail,
                        secondEmail,
                        address,
                        new GregorianCalendar(1999, 0, 5),
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
