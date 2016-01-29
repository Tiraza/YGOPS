package br.com.extractor.ygops.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionButton;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Deck;
import br.com.extractor.ygops.view.ParentFragment;
import br.com.extractor.ygops.view.RealmFragment;
import br.com.extractor.ygops.view.adapter.DecksAdapter;
import br.com.extractor.ygops.view.dialog.AddDeck;
import br.com.extractor.ygops.view.dialog.DialogResult;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Muryllo Tiraza on 27/01/2016.
 */
public class ListDeck extends RealmFragment implements DialogResult {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return onCreateView(inflater, container, R.layout.fragment_list_deck);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupListViewAdapter();

        FloatingActionButton fab = getElementById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddDeck(activity, ListDeck.this);
            }
        });
    }

    @Override
    public void excute() {
        setupListViewAdapter();
    }

    private void setupListViewAdapter(){
        RealmResults<Deck> decks = realm.where(Deck.class).findAll();
        DecksAdapter adapter = new DecksAdapter(decks, activity);
        ListView listView = getElementById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
