package br.com.extractor.ygops.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionButton;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Player;
import br.com.extractor.ygops.view.RealmFragment;
import br.com.extractor.ygops.view.adapter.PlayersAdapter;
import br.com.extractor.ygops.view.dialog.AddPlayer;
import br.com.extractor.ygops.view.dialog.DialogResult;
import io.realm.RealmResults;

/**
 * Created by Muryllo Tiraza on 02/02/2016.
 */
public class ListPlayer extends RealmFragment implements DialogResult {

    private PlayersAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return onCreateView(inflater, container, R.layout.fragment_list);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RealmResults<Player> players = realm.where(Player.class).findAll();
        adapter = new PlayersAdapter(players, activity);
        ListView listView = getElementById(R.id.listView);
        listView.setAdapter(adapter);

        FloatingActionButton fab = getElementById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddPlayer(activity, ListPlayer.this);
            }
        });
    }

    @Override
    public void excute() {
        adapter.notifyDataSetChanged();
    }
}
