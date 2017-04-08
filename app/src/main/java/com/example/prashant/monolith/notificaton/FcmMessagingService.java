package com.example.prashant.monolith.notificaton;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.prashant.monolith.R;
import com.example.prashant.monolith.ui.ImageDetailActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class FcmMessagingService extends FirebaseMessagingService {

    public static final int NOTIFICATION_ID = 1;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {

            try {
                JSONObject jsonObject = new JSONObject(remoteMessage.getData());
                String title = jsonObject.getString("title");
                String image_url = jsonObject.getString("url");
                sendNotification(title, image_url);
            } catch (JSONException e) {
                // JSON parsing failed, so we just let this message go
                e.printStackTrace();
            }
        }
    }

    private void sendNotification(String title, String url) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent =
                PendingIntent.getActivity(this, 0, new Intent(this, ImageDetailActivity.class), 0);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // Notifications using both a large and a small icon (which yours should!) need the large
        // icon as a bitmap. So we need to create that here from the resource ID, and pass the
        // object along in our notification builder. Generally, you want to use the app icon as the
        // small icon, so that users understand what app is triggering this notification.
        final NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Image of the day!")
                        .setSound(soundUri)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentText(title)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        RequestQueue queue = Volley.newRequestQueue(this);

        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                mBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(response));
            }
        }, 0, 0, null, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(imageRequest);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
