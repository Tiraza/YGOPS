package br.com.extractor.ygops.application;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by Muryllo Tiraza on 27/01/2016.
 */
public class YgoPS extends Application {

    private static Realm mRealm;
    private static Tracker mTracker;
    private static final String ANALYTICS_ID = "UA-45287204-4";

    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("ygops.realm")
                .schemaVersion(0)
                .migration(getMigration())
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1800);

        mTracker = analytics.newTracker(ANALYTICS_ID);
        mTracker.enableExceptionReporting(true);
        mTracker.enableAutoActivityTracking(true);
    }

    public static Realm getDefaultRealm() {
        if (mRealm == null) {
            mRealm = Realm.getDefaultInstance();
        }

        return mRealm;
    }

    synchronized public Tracker getDefaultTracker() {
        return mTracker;
    }

    private RealmMigration getMigration () {
        RealmMigration migration = new RealmMigration() {
            @Override
            public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
                RealmSchema schema = realm.getSchema();

                if (oldVersion == 0) {
                    schema.get("Match").addField("otk", Boolean.class);
                    oldVersion++;
                }
            }
        };

        return migration;
    }
}
