package com.gmail.l2t45s7e9.library.repository;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import com.gmail.l2t45s7e9.java.entity.Contact;
import com.gmail.l2t45s7e9.java.interactor.ContactListRepository;
import com.gmail.l2t45s7e9.library.R;
import io.reactivex.rxjava3.core.Single;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ContactListRepositoryImpl implements ContactListRepository {

    private final ContentResolver contentResolver;
    private final Context context;

    public ContactListRepositoryImpl(Context context) {
        contentResolver = context.getContentResolver();
        this.context = context;
    }

    @Override
    public Single<List<Contact>> loadShortInformation(String filterPattern) {
        ContactsRepositoryDelegate contactsRepositoryDelegate = new ContactsRepositoryDelegate(context);
        List<Contact> contacts = new ArrayList<>();
        Set<String> set = new HashSet<>();
        Random random = new Random();
        int[] colors = context.getResources().getIntArray(R.array.colors_list);
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                    null,
                    ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?",
                    new String[]{"%" + filterPattern + "%"},
                    ContactsContract.Contacts.DISPLAY_NAME);
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = contactsRepositoryDelegate.getName(cursor, id);
                String firstNumber = contactsRepositoryDelegate.getNumbers(cursor, id)[0];
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
        return Single.fromCallable(() -> contacts);
    }
}
