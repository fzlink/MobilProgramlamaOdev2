package com.example.someandroidfunc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Uri alarmUri = RingtoneManager.getActualDefaultRingtoneUri(context,RingtoneManager.TYPE_ALARM);
        MediaPlayer mp = MediaPlayer.create(context,alarmUri);
        mp.start();
    }
}
