package com.gmail.l2t45s7e9.empty.repository;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import com.gmail.l2t45s7e9.empty.R;
import com.gmail.l2t45s7e9.empty.domain.ContactListViewModel;
import com.gmail.l2t45s7e9.empty.entity.Contact;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContactListRepository {

    private ContentResolver contentResolver;
    private Context context;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public ContactListRepository(ContentResolver contentResolver, Context context) {
        this.contentResolver = contentResolver;
        this.context = context;
    }


    public void getShortInformation(ContactListViewModel.ShortInformation callback, final String filterPattern) {
        final WeakReference<ContactListViewModel.ShortInformation> ref = new WeakReference<>(callback);
        try {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    List<Contact> result = loadShortInformation(filterPattern);
                    ContactListViewModel.ShortInformation local = ref.get();
                    if (local != null) {
                        local.getContactList(result);
                    }
                }
            }).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } /*finally {
            executorService.shutdown();
        }*/
    }

    private List<Contact> loadShortInformation(String filterPattern) {
        List<Contact> contacts = new ArrayList<>();
        Set<String> set = new HashSet<>();
        Random random = new Random();
        int[] colors = context.getResources().getIntArray(R.array.colors_list);
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?",
                    new String[]{"%" + filterPattern + "%"},
                    ContactsContract.Contacts.DISPLAY_NAME);
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = new ContactsRepositoryDelegate(contentResolver, id).getName(cursor);
                String firstNumber = new ContactsRepositoryDelegate(contentResolver, id).getNumbers(cursor)[0];
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

}
