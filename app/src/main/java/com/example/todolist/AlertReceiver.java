package com.example.todolist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    private static final String TAG = "AlertReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper;
        NotificationCompat.Builder nb;
        if (Reminders.title.length() != 0) {

            Log.d(TAG, "onReceive: "+Reminders.title);
            notificationHelper = new NotificationHelper(context);
            nb = notificationHelper.getChannelNotification(Reminders.title);
        } else {
            notificationHelper = new NotificationHelper(context);
            nb = notificationHelper.getChannelNotification();
        }
        notificationHelper.getManager().notify(1, nb.build());
    }
}
