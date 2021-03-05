package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Reminders extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private static final String TAG = "Reminders";
    private Button openTimePicker;
    private EditText notificationTitle;
    public static String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        getSupportActionBar().hide();

        notificationTitle = findViewById(R.id.alarm_title);

        openTimePicker = findViewById(R.id.time_picker);
        openTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = notificationTitle.getText().toString().trim();
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, i);
        calendar.set(Calendar.MINUTE, i1);
        calendar.set(Calendar.SECOND, 0);

        startAlarm(calendar);
    }

    private void startAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        /*if (title.length() != 0) {
            intent.putExtra("NOTIFICATION_TITLE", title);
            Log.d(TAG, "startAlarm: "+title);
        }*/
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (calendar.before(Calendar.getInstance())) {
            Toast.makeText(this, "Can not set Alarm At the Past", Toast.LENGTH_LONG).show();
            return;
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, "Alarm set Successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}