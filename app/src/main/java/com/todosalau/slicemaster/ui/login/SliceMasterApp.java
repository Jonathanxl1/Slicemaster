package com.todosalau.slicemaster.ui.login;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class SliceMasterApp extends Application {

    @Override
    public void onCreate() {
        FirebaseApp.initializeApp(this);
        super.onCreate();
    }
}
