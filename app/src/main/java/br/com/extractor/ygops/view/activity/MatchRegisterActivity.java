package br.com.extractor.ygops.view.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Deck;
import br.com.extractor.ygops.model.ItemAdapter;
import br.com.extractor.ygops.model.Player;
import br.com.extractor.ygops.util.ColorGenerator;
import br.com.extractor.ygops.view.ParentActivity;
import br.com.extractor.ygops.view.adapter.CustomSpinnerAdapter;
import io.realm.RealmResults;

public class MatchRegisterActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.activity_match_register);

        setupSpinnerDeck();
        setupSpinnerPlayers();
    }

    private void setupSpinnerPlayers() {
        RealmResults<Player> list = realm.where(Player.class).findAll();
        List<ItemAdapter> itens = new ArrayList<>();

        ItemAdapter item = new ItemAdapter(getString(R.string.select_a_player), 0);
        itens.add(item);

        for (Player player : list) {
            item = new ItemAdapter(player.getNome(), player.getColor());
            itens.add(item);
        }

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, itens);
        Spinner spnPlayer = getElementById(R.id.spnPlayer);
        spnPlayer.setAdapter(adapter);
    }

    private void setupSpinnerDeck() {
        RealmResults<Deck> list = realm.where(Deck.class).findAll();
        ArrayList<ItemAdapter> itens = new ArrayList<>();

        ItemAdapter item = new ItemAdapter(getString(R.string.select_a_deck), 0);
        itens.add(item);

        for (Deck deck : list){
            item = new ItemAdapter(deck.getNome(), deck.getColor());
            itens.add(item);
        }

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, itens);

        Spinner spnDeck1 = getElementById(R.id.spnDeck1);
        spnDeck1.setAdapter(adapter);

        Spinner spnDeck2 = getElementById(R.id.spnDeck2);
        spnDeck2.setAdapter(adapter);
    }
}
