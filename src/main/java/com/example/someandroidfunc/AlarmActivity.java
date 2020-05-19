package com.example.someandroidfunc;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    private TextView alarmText;
    private Button setAlarmButton;
    private Button cancelAlarmButton;
    private ImageButton timePickerButton;

    PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        alarmText = findViewById(R.id.alarmTimeText);
        setAlarmButton = findViewById(R.id.setAlarmButton);
        cancelAlarmButton = findViewById(R.id.cancelAlarmButton);
        timePickerButton = findViewById(R.id.alarmTimePickerButton);

        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AlarmActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String t = "";
                                String hour = "";
                                if(hourOfDay < 10)
                                    hour += "0";
                                hour += hourOfDay;
                                String sminute = "";
                                if(minute < 10)
                                    sminute += "0";
                                sminute += minute;

                                t = hour + ":" + sminute;
                                alarmText.setText(t);
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();

            }
        });

        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManager alarmMgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                Calendar c = Calendar.getInstance();
                try {
                    String[] time = alarmText.getText().toString().split(":");
                    c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
                    c.set(Calendar.MINUTE, Integer.parseInt(time[1]));
                    Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                    alarmIntent = PendingIntent.getBroadcast(getApplicationContext(),0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmMgr.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),alarmIntent);
                    Toast.makeText(getApplicationContext(),"Alarm successfully set.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"Alarm set failed.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });

        cancelAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    AlarmManager alarmMgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    alarmMgr.cancel(alarmIntent);
                    Toast.makeText(getApplicationContext(),"Alarm successfully canceled.", Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Alarm cancel failed.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

    }
}
