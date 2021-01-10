package com.gmail.l2t45s7e9.empty.presentation.reciever;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.gmail.l2t45s7e9.empty.R;
import com.gmail.l2t45s7e9.empty.presentation.activity.MainActivity;
import java.util.Calendar;
import java.util.Objects;

public class ContactNotificationsReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "CHANNEL_ID";

<<<<<<< HEAD:Empty/app/src/main/java/com/gmail/l2t45s7e9/empty/presentation/reciever/ContactNotificationsReceiver.java
=======

>>>>>>> master:Empty/app/src/main/java/com/gmail/l2t45s7e9/empty/ContactNotificationsReceiver.java
    @Override
    public void onReceive(Context context, Intent intent) {
        int id = Integer.parseInt(Objects.requireNonNull(intent.getStringExtra("id")));
        int color = intent.getIntExtra("color", -1);
<<<<<<< HEAD:Empty/app/src/main/java/com/gmail/l2t45s7e9/empty/presentation/reciever/ContactNotificationsReceiver.java
        String congratulationTextForNotification = context.getResources().getString(R.string.birthday_notification);
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra("notificationId", String.valueOf(id));
        notificationIntent.putExtra("notificationColor", color);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                id,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        String text = String.format(
                congratulationTextForNotification,
                Objects.requireNonNull(intent.getStringExtra("name"))
        );

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addNextIntent(notificationIntent);

=======
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra("notificationId", id);
        notificationIntent.putExtra("notificationColor", color);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, notificationIntent, 0);
        String text = String.format("Today %s birthday!", intent.getStringExtra("name"));
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addNextIntent(notificationIntent);
>>>>>>> master:Empty/app/src/main/java/com/gmail/l2t45s7e9/empty/ContactNotificationsReceiver.java
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(String.valueOf(id))
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        createChannel(notificationManager);
        notificationManager.notify(id, builder.build());
        setRepeating(intent, context, id);
    }

    private void createChannel(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
<<<<<<< HEAD:Empty/app/src/main/java/com/gmail/l2t45s7e9/empty/presentation/reciever/ContactNotificationsReceiver.java
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID, CHANNEL_ID,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
=======
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
>>>>>>> master:Empty/app/src/main/java/com/gmail/l2t45s7e9/empty/ContactNotificationsReceiver.java
            manager.createNotificationChannel(notificationChannel);
        }
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
