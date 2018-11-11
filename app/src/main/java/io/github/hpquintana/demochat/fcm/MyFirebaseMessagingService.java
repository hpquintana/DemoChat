/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.hpquintana.demochat.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onNewToken(String newToken) {
        super.onNewToken(newToken);

        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            DatabaseReference userReference = mRootRef.child("userInfo").child(user.getUid()).child("confidential/FCM");
            userReference.setValue(newToken, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Log.e(TAG, "Token Refresh Failed Status: " + databaseError.getMessage());
                    } else {
                        Log.e(TAG, "Token Refresh Success");
                    }
                }
            });
        }
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    public static void sendNotification(Context context, String messageBody, String channelUrl) {
        Log.e(TAG, messageBody);
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        final String CHANNEL_ID = "CHANNEL_ID";
//        if (Build.VERSION.SDK_INT >= 26) {  // Build.VERSION_CODES.O
//            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "CHANNEL_NAME", NotificationManager.IMPORTANCE_HIGH);
//            notificationManager.createNotificationChannel(mChannel);
//        }
//
//        Intent intent = new Intent(context, GroupChannelActivity.class);
//        intent.putExtra("groupChannelUrl", channelUrl);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
//                .setSmallIcon(R.drawable.img_notification)
//                .setColor(Color.parseColor("#7469C4"))  // small icon background color
////                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_notification_large))
//                .setContentTitle(context.getResources().getString(R.string.app_name))
//                .setAutoCancel(true)
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText(messageBody))
//                .setSound(defaultSoundUri)
//                .setPriority(Notification.PRIORITY_MAX)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setContentIntent(pendingIntent);
//
//        if (PreferenceUtils.getNotificationsShowPreviews()) {
//            notificationBuilder.setContentText(messageBody);
//        } else {
//            notificationBuilder.setContentText("Somebody sent you a message.");
//        }
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    // [END receive_message]

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        String clickAction = remoteMessage.getData().get("click_action");

        if(clickAction.equalsIgnoreCase("MESSAGE_ACTIVITY")){
            String channelUrl = remoteMessage.getData().get("objectId");;
//        try {
//            channelUrl = remoteMessage.getData().get("objectId");
////            JSONObject sendBird = new JSONObject(remoteMessage.getData().get("sendbird"));
////            JSONObject channel = (JSONObject) sendBird.get("channel");
////            channelUrl = (String) channel.get("channel_url");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
            // Also if you intend on generating your own notifications as a result of a received FCM
            // message, here is where that should be initiated. See sendNotification method below.
            sendNotification(this, remoteMessage.getNotification().getBody(), channelUrl);
        }


    }
}