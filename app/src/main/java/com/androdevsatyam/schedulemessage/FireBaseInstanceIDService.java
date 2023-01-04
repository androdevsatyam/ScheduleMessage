package com.androdevsatyam.schedulemessage;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FireBaseInstanceIDService extends FirebaseMessagingService {

    String TAG = "FirebaseService";
    String key;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        key = s;
        Log.i(TAG, "NewToken=" + s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.v(TAG, "MsgReceived=" + remoteMessage.getCollapseKey() + remoteMessage.getFrom());
        Log.v(TAG, "Notification Message TITLE: " + remoteMessage.getNotification().getTitle());
        Log.v(TAG, "Notification Message BODY: " + remoteMessage.getNotification().getBody());
        Log.v(TAG, "Notification Message DATA: " + remoteMessage.getData().toString());
        Log.v(TAG, "Notification Message Time: " + remoteMessage.getSentTime());

        DataModel model=new  DataModel(this);
        model.setData(remoteMessage.getNotification().getBody().split("_")[0],remoteMessage.getNotification().getBody().split("_")[1]);

        sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage.getData());
    }

    //This method is only generating push notificationGetting data and create notification for app
    private void sendNotification(String messageTitle, String messageBody, Map<String, String> row) {
        Intent intent;
        PendingIntent contentIntent;

        if (row.containsKey("type") && row.containsKey("target")) {
            if (row.get("type").equalsIgnoreCase("click")) {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(row.get("target")));
                contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
            }else {
                intent = new Intent(this, MainActivity.class);
                contentIntent = PendingIntent.getActivity(this, 1, intent, 0);
            }
        } else {
            intent = new Intent(this, MainActivity.class);
            contentIntent = PendingIntent.getActivity(this, 1, intent, 0);
        }
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(contentIntent);
        Notification notification = notificationBuilder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

    }

}
