package com.example.shiping.glut;

import com.firebase.client.Firebase;

/**
 * Created by Admin on 1/24/2016.
 */
public class ChatApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
