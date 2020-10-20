package com.gmail.l2t45s7e9.empty;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.lang.ref.WeakReference;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContactService extends Service {
    private final IBinder mBinder = new LocalBinder();
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private Contact[] contacts;

    @Override
    public void onCreate() {
        super.onCreate();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        Random random = new Random();
        int[] colors = getApplicationContext().getResources().getIntArray(R.array.colors_list);
        Contact contact1 = new Contact(
                "Анатолий Анатольевич",
                "+795131351",
                "+7561516",
                "email1",
                "email2",
                "г Ижевск Улица Пушкинская дом 112",
                new GregorianCalendar(0, 9, 11),
                colors[random.nextInt(colors.length)]);
        Contact contact2 = new Contact(
                "Иван Иванов",
                "+795641516",
                "+76161568",
                "email1",
                "email2",
                "г Ижевск Улица Ленина дом 0",
                new GregorianCalendar(0, 9, 12),
                colors[random.nextInt(colors.length)]);
        Contact contact3 = new Contact(
                "Иван Иванов 2",
                "+795641516",
                "+76161568",
                "email1",
                "email2",
                "г Ижевск Улица Ленина дом 0",
                new GregorianCalendar(0, 9, 20),
                colors[random.nextInt(colors.length)]);

        contacts = new Contact[]{contact1, contact2, contact3};
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
                Contact[] result = contacts;
                ContactListFragment.ShortInformation local = ref.get();
                if (local != null) {
                    local.getContactList(result);
                }
            }
        });
    }

    public void getDetailsInformation(ContactDetailsFragment.DetailsInformation callback, final int position) {
        final WeakReference<ContactDetailsFragment.DetailsInformation> ref = new WeakReference<>(callback);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Contact result = contacts[position];
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
}
