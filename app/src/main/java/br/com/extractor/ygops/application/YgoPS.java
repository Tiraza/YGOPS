package br.com.extractor.ygops.application;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Muryllo Tiraza on 27/01/2016.
 */
public class YgoPS extends Application {

    private static Realm mRealm;
    private static Tracker mTracker;
    private static Context mContext;
    private static final String ANALYTICS_ID = "UA-45287204-4";

    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("ygops.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        mContext = getApplicationContext();
        mRealm = Realm.getDefaultInstance();
    }

    public static Realm getDefaultRealm(){
        return mRealm;
    }

    public static Context getDefaultContext() {
        return mContext;
    }

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            analytics.setLocalDispatchPeriod(1800);

            mTracker = analytics.newTracker(ANALYTICS_ID);
            mTracker.enableExceptionReporting(true);
            mTracker.enableAutoActivityTracking(true);
        }
        return mTracker;
    }
}
