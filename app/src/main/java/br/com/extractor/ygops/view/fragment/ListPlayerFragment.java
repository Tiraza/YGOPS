package br.com.extractor.ygops.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionButton;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Player;
import br.com.extractor.ygops.view.RealmFragment;
import br.com.extractor.ygops.view.activity.PlayerRegisterActivity;
import br.com.extractor.ygops.view.adapter.PlayersAdapter;
import io.realm.RealmResults;

/**
 * Created by Muryllo Tiraza on 02/02/2016.
 */
public class ListPlayerFragment extends RealmFragment {

    private PlayersAdapter adapter;
    private static int PLAYER_REGISTER_CODE = 1;

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
                Intent intent = new Intent(activity, PlayerRegisterActivity.class);
                startActivityForResult(intent, PLAYER_REGISTER_CODE);
            }
        });
        fab.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fab_scale_in));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == PLAYER_REGISTER_CODE) {
            adapter.notifyDataSetChanged();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
