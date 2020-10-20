package com.gmail.l2t45s7e9.empty;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class ContactNotificationsReceiver extends BroadcastReceiver {


    private static final String CHANNEL_ID = "CHANNEL_ID";

    public static void createChannel(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra("id", -1);
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra("notificationId", id);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, notificationIntent, 0);
        String text = String.format("Today %s birthday!", intent.getStringExtra("name"));
        //Toast.makeText(context, String.valueOf(i), Toast.LENGTH_SHORT).show();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(String.valueOf(id))
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        createChannel(notificationManager);
        notificationManager.notify(1, builder.build());
        setRepeating(intent, context, id);
    }

    public void setRepeating(Intent intent, Context context, int id) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, id, intent, 0);
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) == Calendar.FEBRUARY && calendar.get(Calendar.DATE) == 29) {
            calendar.add(Calendar.YEAR, 4);
        } else {
            calendar.add(Calendar.DATE, 1);
        }
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), alarmIntent);
    }

}
