package com.gmail.l2t45s7e9.empty;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.IBinder;
import android.provider.ContactsContract;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContactService extends Service {
    private final IBinder mBinder = new LocalBinder();
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private List<Contact> arrayList;
    @Override
    public void onCreate() {
        super.onCreate();
        arrayList = loadShortInformation();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void getShortInformation(ContactListFragment.ShortInformation callback) {
        final WeakReference<ContactListFragment.ShortInformation> ref = new WeakReference<>(callback);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                List<Contact> result = arrayList;
                ContactListFragment.ShortInformation local = ref.get();
                if (local != null) {
                    local.getContactList(result);
                }
            }
        });
    }

    public void getDetailsInformation(ContactDetailsFragment.DetailsInformation callback, final String id, final int color) {
        final WeakReference<ContactDetailsFragment.DetailsInformation> ref = new WeakReference<>(callback);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Contact result = loadDetailsInformation(id, color);
                ContactDetailsFragment.DetailsInformation local = ref.get();
                if (local != null) {
                    local.getDetails(result);
                }
            }
        });
    }

    interface PublicServiceInterface {
        ContactService getService();
    }

    public class LocalBinder extends Binder {
        ContactService getService() {
            return ContactService.this;
        }
    }

    @Override
    public void onDestroy() {
        executorService.shutdown();
        super.onDestroy();
    }

    public String[] getNumbers(String id) {
        String[] number = new String[2];
        int count = 0;
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(
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

    public String[] getEmails(String id) {
        String[] email = new String[2];
        int count = 0;
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(
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
        int[] colors = getApplicationContext().getResources().getIntArray(R.array.colors_list);
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
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

    public Contact loadDetailsInformation(String id, int color) {
        Contact contact = null;
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(
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
                        new GregorianCalendar(1999, 0, 2),
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
