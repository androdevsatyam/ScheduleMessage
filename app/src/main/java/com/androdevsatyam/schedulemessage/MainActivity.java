package com.androdevsatyam.schedulemessage;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DataModel model;
    String msg;
    String[] numbers;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = new DataModel(this);
        numbers = model.getData("num").split(",");
        msg = model.getData("msg");
        if (numbers.length > 1)
            startSending(numbers[i]);

        if (checkpermissions()) {
            Toast.makeText(this, "Good to Go", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Persmission is Neccassary to send sms", Toast.LENGTH_SHORT).show();
    }

    private void startSending(String number) {
        sendSmsByManager(this, number, msg);
        try {
            Thread.sleep(800);
            i++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (i < numbers.length)
            startSending(numbers[i]);
    }

    protected boolean checkpermissions() {
        boolean result = false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
                result = true;
            else {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 101);
            }
        } else
            result = true;

        return result;
    }

    public static void sendSMS(Context context, String phoneNumber, String message, SubscriptionInfo simInfo) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";
        sendSmsByManager(context, phoneNumber, message);

//        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, new Intent(SENT), 0);
//
//        PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0, new Intent(DELIVERED), 0);
//        SmsManager sms = null;
//        try
//        {
//            if (simInfo != null) {
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
//                    if (/*!getSmsCount()*/true) {
//                        if (/*sendwithbal()*/true) {
//                            sms = SmsManager.getSmsManagerForSubscriptionId(simInfo.getSubscriptionId());
//                            ArrayList<String> arrSMS = sms.divideMessage(message);
//                            sms.sendMultipartTextMessage(phoneNumber, null, arrSMS, null, null);
//                        } else {
////                            Toast.makeText(mcontext, "can't send sms", Toast.LENGTH_SHORT).show();
//                            Log.d("TAG", "sendSMS:can't send sms ");
//                        }
//                    }
//                    else
//                    {
//                        sms = SmsManager.getSmsManagerForSubscriptionId(simInfo.getSubscriptionId());
//                        ArrayList<String> arrSMS = sms.divideMessage(message);
//                        sms.sendMultipartTextMessage(phoneNumber, null, arrSMS, null, null);
//                    }
//                    //sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
//                    //Toast.makeText(getApplicationContext(), "SIM: "+getCache("SIMID",getApplicationContext()) + "\nSMS: "+getCache("smsText",getApplicationContext()), Toast.LENGTH_SHORT).show();
//
//                }
//                else {
//                    sendSmsByManager(context,phoneNumber, message);
////                      sendSmsByManager(phoneNumber, arrSMS);
//                    //Toast.makeText(getApplicationContext(), "below M", Toast.LENGTH_SHORT).show();
//                }
//            }
//            else{
//                // Toast.makeText(getApplicationContext(), "Sim Data Null", Toast.LENGTH_SHORT).show();
//                Log.d("MainActivity ","Sim Data is null");
//                sendSmsByManager(context,phoneNumber, message);
////            sendSmsByManager(phoneNumber, arrSMS);
//            }
//        }
//        catch (Exception e)
//        {
//            e.getStackTrace();
//        }
    }

    public static void sendSmsByManager(Context mcontext, String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            //smsManager.sendTextMessage(phoneNumber,null,message,null,null);
            ArrayList<String> arrSMS = smsManager.divideMessage(message);
            smsManager.sendMultipartTextMessage(phoneNumber, null, arrSMS, null, null);
        } catch (Exception ex) {
//            Toast.makeText(mcontext,"Error: "+ex,Toast.LENGTH_SHORT).show();
            Log.d("TAG", "sendSmsByManager: ex");
            ex.printStackTrace();
        }
    }
}