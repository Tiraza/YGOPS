package br.com.extractor.ygops.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Muryllo Tiraza on 27/01/2016.
 */
public class Player extends RealmObject {

    @PrimaryKey
    private String uuid;
    private String nome;
    private Integer color;
    private RealmList<Deck> decks;

    public Player() {}

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

    public Integer getColor() {
        return color;
    }
    public void setColor(Integer color) {
        this.color = color;
    }
}
