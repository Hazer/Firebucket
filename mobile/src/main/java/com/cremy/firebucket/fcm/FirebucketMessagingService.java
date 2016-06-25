package com.cremy.firebucket.fcm;

import com.cremy.greenrobotutils.library.notification.GreenRobotNotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by remychantenay on 25/06/2016.
 */
public class FirebucketMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
    }
}
