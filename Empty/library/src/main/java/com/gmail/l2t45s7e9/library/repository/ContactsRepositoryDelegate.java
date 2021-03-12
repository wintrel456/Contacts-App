package com.gmail.l2t45s7e9.library.repository;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ContactsRepositoryDelegate {
    private final ContentResolver contentResolver;
    private final String id;

    public ContactsRepositoryDelegate(ContentResolver contentResolver, String id) {
        this.contentResolver = contentResolver;
        this.id = id;
    }

    public String[] getNumbers(Cursor cursor) {
        String[] number = new String[2];
        int count = 0;
        try {
            cursor = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                    null,
                    null
            );
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

    public String[] getEmails(Cursor cursor) {
        String[] email = new String[2];
        int count = 0;
        try {
            cursor = contentResolver.query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + id,
                    null,
                    null
            );
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

    public GregorianCalendar getBirthDate(Cursor cursor) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        String birthDate = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            cursor = contentResolver.query(
                    ContactsContract.Data.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Event.CONTACT_ID + "=" + id + " AND " +
                            ContactsContract.CommonDataKinds.Event.TYPE + "=" +
                            ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY,
                    null,
                    null
            );
            while (cursor.moveToNext()) {
                birthDate = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        try {
            if (birthDate != null) {
                Date date = simpleDateFormat.parse(birthDate);
                gregorianCalendar.setTime(date);
            } else {
                gregorianCalendar = null;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            gregorianCalendar = null;
        }

        return gregorianCalendar;
    }

    public String getName(Cursor cursor) {
        String name = null;
        try {
            cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                    null,
                    ContactsContract.Contacts._ID + "=" + id,
                    null,
                    ContactsContract.Contacts.DISPLAY_NAME);
            while (cursor.moveToNext()) {
                name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return name;
    }

}

