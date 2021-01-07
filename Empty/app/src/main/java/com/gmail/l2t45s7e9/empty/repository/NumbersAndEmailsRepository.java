package com.gmail.l2t45s7e9.empty.repository;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

public class NumbersAndEmailsRepository {

    private final ContentResolver contentResolver;
    private final String id;

    public NumbersAndEmailsRepository(ContentResolver contentResolver, String id) {
        this.contentResolver = contentResolver;
        this.id = id;
    }

    public String[] loadNumbers() {
        return getNumbers();
    }

    public String[] loadEmails() {
        return getEmails();
    }

    private String[] getNumbers() {
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

    private String[] getEmails() {
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
}

