package br.com.extractor.ygops.view;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;

/**
 * Created by Muryllo Tiraza on 29/01/2016.
 */
public abstract class RealmFragment extends ParentFragment {
    protected Realm realm;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(realm != null){
            realm = Realm.getDefaultInstance();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        realm.close();
    }

    protected <T extends RealmObject> T consult(Class<T> tClass, Long idObject){
        RealmQuery<T> query = realm.where(tClass);
        query = query.equalTo("id", idObject);
        return query.findFirst();
    }
}
