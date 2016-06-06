package br.com.extractor.ygops.util;

import br.com.extractor.ygops.application.YgoPS;
import br.com.extractor.ygops.model.Deck;
import br.com.extractor.ygops.model.Match;
import br.com.extractor.ygops.model.Player;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Muryllo Tiraza on 29/01/2016.
 */
public final class RealmUtils {

    private Realm realm;
    private static RealmUtils INSTANCE = new RealmUtils();

    private RealmUtils() {
        if (realm == null) {
            realm = YgoPS.getDefaultRealm();
        }
    }

    public static RealmUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RealmUtils();
        }

        return INSTANCE;
    }

    public Realm getRealm() {
        return realm;
    }

    public <T extends RealmObject> T get(Class<T> tClass) {
        T result = realm.where(tClass).findFirst();
        result.load();
        return result;
    }

    public <T extends RealmObject> T getForUuid(Class<T> tClass, String uuid) {
        T result = realm.where(tClass).equalTo("uuid", uuid).findFirstAsync();
        result.load();
        return result;
    }

    public <T extends RealmObject> T getForName(Class<T> tClass, String nome) {
        T result = realm.where(tClass).equalTo("nome", nome).findFirstAsync();
        result.load();
        return result;
    }

    public <T extends RealmObject> RealmResults<T> getAll(Class<T> tClass) {
        RealmResults<T> results = realm.where(tClass).findAllAsync();
        results.load();
        return results;
    }

    public <T extends RealmObject> void insert(final T object) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(object);
        realm.commitTransaction();
    }

    public <T extends RealmObject> void update(final T object) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(object);
        realm.commitTransaction();
    }

    public void removeDeckForUuid(String uuid) {
        RealmQuery<Match> query = realm.where(Match.class);
        query.equalTo("deck.uuid", uuid).or().equalTo("playerDeck.uuid", uuid);

        if (query.findAll().isEmpty()) {
            realm.beginTransaction();
            realm.where(Deck.class).equalTo("uuid", uuid).findAll().clear();
            realm.commitTransaction();
        }
    }

    public void removePlayerForUuid(String uuid){
        RealmQuery<Match> query = realm.where(Match.class);
        query.equalTo("player.uuid", uuid);

        if (query.findAll().isEmpty()) {
            realm.beginTransaction();
            realm.where(Player.class).equalTo("uuid", uuid).findAll().clear();
            realm.commitTransaction();
        }
    }
}
