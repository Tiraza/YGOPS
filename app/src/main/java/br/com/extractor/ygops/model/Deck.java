package br.com.extractor.ygops.model;

import io.realm.RealmObject;

/**
 * Created by Muryllo Tiraza on 27/01/2016.
 */
public class Deck extends RealmObject {

    private String uuid;
    private String nome;

    public Deck() {}

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
}
