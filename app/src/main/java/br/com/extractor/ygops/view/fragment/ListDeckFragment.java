package br.com.extractor.ygops.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Deck;
import br.com.extractor.ygops.model.Match;
import br.com.extractor.ygops.view.RealmFragment;
import br.com.extractor.ygops.view.activity.MainActivity;
import br.com.extractor.ygops.view.activity.consult.DeckConsultActivity;
import br.com.extractor.ygops.view.activity.register.DeckRegisterActivity;
import br.com.extractor.ygops.view.adapter.DecksAdapter;
import br.com.extractor.ygops.view.adapter.DecksDeleteAdapter;
import br.com.extractor.ygops.view.interfaces.DeleteAdapter;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Muryllo Tiraza on 27/01/2016.
 */
public class ListDeckFragment extends RealmFragment implements DeleteAdapter {

    private DecksAdapter adapter;
    private DecksDeleteAdapter deleteAdapter;
    private MenuItem menuDelete;
    private ListView listView;
    private FloatingActionButton fab;

    private static int DECK_REGISTER_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return onCreateView(inflater, container, R.layout.fragment_list);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        activity.setTitle(R.string.decks);
        ((MainActivity) activity).toggleIconToolbar(true);

        setupFab();
        setupListView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DECK_REGISTER_CODE) {
            adapter.notifyDataSetChanged();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_delete, menu);

        menuDelete = menu.findItem(R.id.menuDelete);
        menuDelete.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuDelete) {
            RealmQuery<Match> queryMatch = realm.where(Match.class);
            queryMatch.equalTo("deck.nome", "");

            for (Deck deck : deleteAdapter.getSelectedItens()) {
                queryMatch.or().equalTo("deck.nome", deck.getNome());
                queryMatch.or().equalTo("playerDeck.nome", deck.getNome());
            }

            if (queryMatch.findAll().isEmpty()) {
                realm.beginTransaction();
                RealmQuery<Deck> query = realm.where(Deck.class);
                query.equalTo("nome", "");

                for (Deck deck : deleteAdapter.getSelectedItens()) {
                    query.or().equalTo("nome", deck.getNome());
                }

                query.findAll().clear();
                realm.commitTransaction();

                RealmResults<Deck> decks = realm.where(Deck.class).findAll();
                decks.sort("nome", Sort.ASCENDING);
                adapter = new DecksAdapter(activity, decks, true);
                listView.setAdapter(adapter);
                menuDelete.setVisible(false);
            } else {
                makeToast(R.string.record_already_used, Toast.LENGTH_LONG);
                onDelete();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDelete() {
        listView.setAdapter(adapter);
        menuDelete.setVisible(false);
    }

    private void setupFab() {
        fab = getElementById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DeckRegisterActivity.class);
                startActivityForResult(intent, DECK_REGISTER_CODE);

            }
        });
        fab.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fab_scale_in));
    }

    private void setupListView() {
        RealmResults<Deck> decks = realm.where(Deck.class).findAll();
        decks.sort("nome", Sort.ASCENDING);
        adapter = new DecksAdapter(activity, decks, true);

        listView = getElementById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                RealmResults<Deck> decks = realm.where(Deck.class).findAll();
                deleteAdapter = new DecksDeleteAdapter(decks, activity, position, ListDeckFragment.this);
                listView.setAdapter(deleteAdapter);
                menuDelete.setVisible(true);
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Deck deck = adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("deckName", deck.getNome());
                Intent intent = new Intent(activity, DeckConsultActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mLastFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {}

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mLastFirstVisibleItem < firstVisibleItem) {
                    fab.hide(true);
                }
                if (mLastFirstVisibleItem > firstVisibleItem) {
                    fab.show(true);
                }
                mLastFirstVisibleItem = firstVisibleItem;
            }
        });
    }
}
