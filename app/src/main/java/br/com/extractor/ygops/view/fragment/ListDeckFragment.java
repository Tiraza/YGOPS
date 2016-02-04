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
import br.com.extractor.ygops.model.Deck;
import br.com.extractor.ygops.view.RealmFragment;
import br.com.extractor.ygops.view.activity.DeckRegisterActivity;
import br.com.extractor.ygops.view.adapter.DecksAdapter;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Muryllo Tiraza on 27/01/2016.
 */
public class ListDeckFragment extends RealmFragment {

    private DecksAdapter adapter;
    private static int DECK_REGISTER_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return onCreateView(inflater, container, R.layout.fragment_list);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RealmResults<Deck> decks = realm.where(Deck.class).findAll();
        decks.sort("nome", Sort.ASCENDING);

        adapter = new DecksAdapter(decks, activity);
        ListView listView = getElementById(R.id.listView);
        listView.setAdapter(adapter);

        final FloatingActionButton fab = getElementById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DeckRegisterActivity.class);
                startActivityForResult(intent, DECK_REGISTER_CODE);

            }
        });
        fab.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fab_scale_in));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == DECK_REGISTER_CODE){
            adapter.notifyDataSetChanged();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
