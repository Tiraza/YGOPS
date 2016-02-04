package br.com.extractor.ygops.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Muryllo Tiraza on 02/02/2016.
 */
public class Profile extends RealmObject {

    @PrimaryKey
    private String uuid;
    private String nome;
    private RealmList<Deck> decks;

    public Profile() {}

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public RealmList<Deck> getDecks() {
        return decks;
    }
    public void setDecks(RealmList<Deck> decks) {
        this.decks = decks;
    }
}
