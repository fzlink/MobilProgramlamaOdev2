package com.example.someandroidfunc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SmsBroadcastReceiver";

    String msg, phoneNo = "";
    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(calendar.getTime());
        File file = new File(context.getFilesDir(),"logs");
        if (!file.exists()) {
            file.mkdir();
        }
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        try{
            if(state != null && state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                String incoming_number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

                Toast.makeText(context,incoming_number + "\n saved to callLogs.txt",Toast.LENGTH_LONG).show();
                File callLog = new File(file, "callLogs.txt");
                FileWriter writer = new FileWriter(callLog,true);
                writer.append(incoming_number + ", " + date + "\n");
                writer.flush();
                writer.close();


            }

            else if(intent.getAction() == SMS_RECEIVED){
                Bundle dataBundle = intent.getExtras();
                if(dataBundle != null){
                    Object[] mypdu = (Object[])dataBundle.get("pdus");
                    final SmsMessage[] message = new SmsMessage[mypdu.length];

                    for(int i = 0;i <mypdu.length;i++){
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            String format = dataBundle.getString("format");
                            message[i] = SmsMessage.createFromPdu((byte[])mypdu[i], format);
                        }
                        else{
                            message[i] = SmsMessage.createFromPdu((byte[]) mypdu[i]);
                        }
                        msg = message[i].getMessageBody();
                        phoneNo = message[i].getOriginatingAddress();
                    }
                    Toast.makeText(context, "Message: " + msg + "\n Number: " + phoneNo + "\n saved to smsLogs.txt", Toast.LENGTH_LONG).show();
                    File smsLog = new File(file,"smsLogs.txt");
                    FileWriter writer = new FileWriter(smsLog,true);
                    writer.append(phoneNo + ", " + msg + ", " + date + "\n");
                    writer.flush();
                    writer.close();
                }
            }
        }catch(Exception e){e.printStackTrace();}

    }
}
