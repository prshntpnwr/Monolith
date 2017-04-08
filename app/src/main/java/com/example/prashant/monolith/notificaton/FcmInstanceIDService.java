package com.example.prashant.monolith.notificaton;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FcmInstanceIDService extends FirebaseInstanceIdService{

    @Override
    public void onTokenRefresh() {
        Log.i("FCM Token ", FirebaseInstanceId.getInstance().getToken());
    }
}
