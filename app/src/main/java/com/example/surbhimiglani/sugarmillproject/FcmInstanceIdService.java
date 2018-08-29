package com.example.surbhimiglani.sugarmillproject;

/**
 * Created by Surbhi Miglani on 25-04-2018.
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by Surbhi Miglani on 10-03-2018.
 */

public class FcmInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String recent_token= FirebaseInstanceId.getInstance().getToken();
        SharedPreferences sf=getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sf.edit();
        editor.putString(getString(R.string.FCM_TOKEN), recent_token);
        editor.apply();
        FirebaseMessaging.getInstance().subscribeToTopic("all");

    }
}
