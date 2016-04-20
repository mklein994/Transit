package com.example.matthew.transit;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by matthew on 11/04/16.
 */
public class TransitApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration config = new RealmConfiguration.Builder(this).build();

        Realm.deleteRealm(config);

        Realm.setDefaultConfiguration(config);
    }
}
