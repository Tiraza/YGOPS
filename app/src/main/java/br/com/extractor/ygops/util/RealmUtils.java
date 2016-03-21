package br.com.extractor.ygops.util;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Muryllo Tiraza on 29/01/2016.
 */
public final class RealmUtils {

    private static RealmUtils INSTANCE = new RealmUtils();

    private RealmUtils() {}

    public static RealmUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RealmUtils();
        }
        return INSTANCE;
    }

    public <T extends RealmObject> T get(Class<T> tClass) {
        Realm realm = Realm.getDefaultInstance();
        T result = realm.where(tClass).findFirstAsync();
        result.load();
        realm.close();
        return result;
    }

    public <T extends RealmObject> T get(Class<T> tClass, String uuid) {
        Realm realm = Realm.getDefaultInstance();
        T result = realm.where(tClass).equalTo("uuid", uuid).findFirstAsync();
        result.load();
        realm.close();
        return result;
    }

    public <T extends RealmObject> RealmResults<T> getAll(Class<T> tClass) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<T> results = realm.where(tClass).findAllAsync();
        results.load();
        realm.close();
        return results;
    }

    public <T extends RealmObject> void insert(final T object) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(object);
        realm.commitTransaction();
        realm.close();
    }

    public <T extends RealmObject> void update(final T object) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(object);
        realm.commitTransaction();
        realm.close();
    }

}
