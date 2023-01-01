package com.androdevsatyam.schedulemessage;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkpermissions()) {
            Toast.makeText(this,"Good to Go",Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this,"Persmission is Neccassary to send sms",Toast.LENGTH_SHORT).show();
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
}