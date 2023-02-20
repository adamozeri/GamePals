package com.example.gamepals.Utils;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SignalSingleton.init(this);
    }
}
