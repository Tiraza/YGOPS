package br.com.extractor.ygops.util;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by Muryllo Tiraza on 29/01/2016.
 */
public class RealmUtils {

    public static <T extends RealmObject>  void insert(T object){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(object);
        realm.commitTransaction();
        realm.close();
    }

    public static <T extends RealmObject>  T get(Class<T> tClass) {
        Realm realm = Realm.getDefaultInstance();
        T result = realm.where(tClass).findFirstAsync();
        realm.close();
        return result;
    }

}
