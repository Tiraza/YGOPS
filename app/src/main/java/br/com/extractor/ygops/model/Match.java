package br.com.extractor.ygops.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Muryllo Tiraza on 27/01/2016.
 */
public class Match extends RealmObject {

    @PrimaryKey
    private String uuid;

    private Date date;
    private Deck deck;

    private Player player;
    private Deck playerDeck;

    private Boolean winner;

    public Match() {}

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Deck getDeck() {
        return deck;
    }
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }

    public Deck getPlayerDeck() {
        return playerDeck;
    }
    public void setPlayerDeck(Deck playerDeck) {
        this.playerDeck = playerDeck;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getWinner() {
        return winner;
    }
    public void setWinner(Boolean winner) {
        this.winner = winner;
    }
}
