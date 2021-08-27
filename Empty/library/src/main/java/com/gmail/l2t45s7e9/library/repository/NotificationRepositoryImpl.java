package com.gmail.l2t45s7e9.library.repository;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.gmail.l2t45s7e9.java.entity.Contact;
import com.gmail.l2t45s7e9.java.interactor.NotificationRepository;
import com.gmail.l2t45s7e9.library.presentation.reciever.ContactNotificationsReceiver;

import java.util.GregorianCalendar;

public class NotificationRepositoryImpl implements NotificationRepository {

    private AlarmManager alarmManager;
    private Context context;
    private Intent intent;

    public NotificationRepositoryImpl(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        intent = new Intent(context, ContactNotificationsReceiver.class);
    }

    private PendingIntent setManagerAndIntent(String position) {
        return PendingIntent.getBroadcast(context, Integer.parseInt(position), intent, 0);
    }

    @Override
    public boolean status(String position) {
        return PendingIntent.getBroadcast(
                context,
                Integer.parseInt(position),
                intent, PendingIntent.FLAG_NO_CREATE
        ) != null;
    }

    @Override
    public void setManager(Contact contact, GregorianCalendar calendar) {
        intent.putExtra("name", contact.getName());
        intent.putExtra("id", contact.getId());
        intent.putExtra("color", contact.getContactColor());
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), setManagerAndIntent(contact.getId()));
    }

    @Override
    public void cancelManager(Contact contact) {
        PendingIntent pendingIntent = setManagerAndIntent(contact.getId());
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }
}
