package com.sam_chordas.android.stockhawk;

import android.app.Application;
import android.content.res.Configuration;

import java.util.Locale;

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }
}
