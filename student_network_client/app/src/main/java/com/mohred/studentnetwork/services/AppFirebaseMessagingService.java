package com.mohred.studentnetwork.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mohred.studentnetwork.R;
import com.mohred.studentnetwork.common.ActivityTutorAcceptedNotification;
import com.mohred.studentnetwork.page_tutors.ActivityTutors;

import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.JSON_KEY_COURSE_NAME;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.JSON_KEY_IS_ACCEPTED;
import static com.mohred.studentnetwork.common.AppConstants.ConnectionConstants.JSON_KEY_TUTOR_ID;
import static com.mohred.studentnetwork.common.AppConstants.ScreenArguments.ARG_GOTO_REQUESTS;

/**
 * Created by Redan on 2/17/2017.
 */
public class AppFirebaseMessagingService extends FirebaseMessagingService
{
    private static final String TAG = "firebase_msging_service";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getNotification()!=null)
        {
            if(remoteMessage.getData() != null) {
                String tutorID = remoteMessage.getData().get(JSON_KEY_TUTOR_ID);
                String courseID = remoteMessage.getData().get(JSON_KEY_COURSE_NAME);
                String isAccepted = remoteMessage.getData().get(JSON_KEY_IS_ACCEPTED);
                sendNotification(remoteMessage.getNotification().getBody(),tutorID,courseID,isAccepted);
            }else{
                sendNotification(remoteMessage.getNotification().getBody(),null,null,null);
            }
        }
    }

    private void sendNotification(String body,String tutorID,String courseName,String isAccepted)
    {
        Intent intent;
        if(tutorID != null){
            intent = new Intent(this, ActivityTutorAcceptedNotification.class);
            Bundle arguments = new Bundle();
            arguments.putString(JSON_KEY_TUTOR_ID,tutorID);
            arguments.putString(JSON_KEY_COURSE_NAME,courseName);
            arguments.putString(JSON_KEY_IS_ACCEPTED,isAccepted);
            intent.putExtras(arguments);
        }else{
            intent = new Intent(this, ActivityTutors.class);
            Bundle arguments = new Bundle();
            arguments.putString(ARG_GOTO_REQUESTS,ARG_GOTO_REQUESTS);
            intent.putExtras(arguments);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(this)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(getString(R.string.app_name))
                                .setContentText(body)
                                .setAutoCancel(true)
                                .setSound(notificationSound)
                                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                                   (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationCompat.build());

    }
}
