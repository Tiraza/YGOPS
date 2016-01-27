package br.com.extractor.ygops.model;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by Muryllo Tiraza on 27/01/2016.
 */
public class Match extends RealmObject {

    private Player winner;
    private Player loser;

    private Deck deckWinner;
    private Deck deckLoser;

    private Date date;

    public Match() {}

    public Player getWinner() {
        return winner;
    }
    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Deck getDeckWinner() {
        return deckWinner;
    }
    public void setDeckWinner(Deck deckWinner) {
        this.deckWinner = deckWinner;
    }

    public Player getLoser() {
        return loser;
    }
    public void setLoser(Player loser) {
        this.loser = loser;
    }

    public Deck getDeckLoser() {
        return deckLoser;
    }
    public void setDeckLoser(Deck deckLoser) {
        this.deckLoser = deckLoser;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
