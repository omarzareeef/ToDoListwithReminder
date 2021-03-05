package com.example.todolist;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    private static final String TAG = "NotificationHelper";
    public static final String channel1ID = "channel1ID";
    public static final String channel1Name = "channel 1";

    private NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannels() {
        NotificationChannel channel1 = new NotificationChannel(channel1ID, channel1Name, NotificationManager.IMPORTANCE_DEFAULT);
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel1);
    }

    public NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public NotificationCompat.Builder getChannelNotification() {
        return new NotificationCompat.Builder(getApplicationContext(),channel1ID)
                .setSmallIcon(R.drawable.alarm2);
    }

    public NotificationCompat.Builder getChannelNotification(String title) {
        Log.d(TAG, "omar3: "+title);
        return new NotificationCompat.Builder(getApplicationContext(),channel1ID)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.alarm2);
    }
}
