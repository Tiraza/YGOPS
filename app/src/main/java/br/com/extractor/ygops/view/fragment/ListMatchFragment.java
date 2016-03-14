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

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Match;
import br.com.extractor.ygops.view.RealmFragment;
import br.com.extractor.ygops.view.activity.MainActivity;
import br.com.extractor.ygops.view.activity.register.MatchRegisterActivity;
import br.com.extractor.ygops.view.adapter.MatchesAdapter;
import br.com.extractor.ygops.view.adapter.MatchesDeleteAdapter;
import br.com.extractor.ygops.view.interfaces.DeleteAdapter;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Muryllo Tiraza on 05/02/2016.
 */
public class ListMatchFragment extends RealmFragment implements DeleteAdapter {

    private MatchesAdapter adapter;
    private MatchesDeleteAdapter deleteAdapter;
    private MenuItem menuDelete;
    private ListView listView;
    private FloatingActionButton fab;

    private static int MATCH_REGISTER_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return onCreateView(inflater, container, R.layout.fragment_list);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        activity.setTitle(R.string.matches);
        ((MainActivity)activity).toggleIconToolbar(true);

        setupFab();
        setupListView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MATCH_REGISTER_CODE) {
            adapter = new MatchesAdapter(reverse(realm.where(Match.class).findAll()), activity, realm);
            listView.setAdapter(adapter);
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
            realm.beginTransaction();
            RealmQuery<Match> query = realm.where(Match.class);
            query.equalTo("uuid", "");

            for (Match player : deleteAdapter.getSelectedItens()) {
                query.or().equalTo("uuid", player.getUuid());
            }

            query.findAll().clear();
            realm.commitTransaction();

            adapter = new MatchesAdapter(reverse(realm.where(Match.class).findAll()), activity, realm);
            listView.setAdapter(adapter);
            menuDelete.setVisible(false);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDelete() {
        listView.setAdapter(adapter);
        menuDelete.setVisible(false);
    }

    private void setupFab(){
        fab = getElementById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, MatchRegisterActivity.class);
                startActivityForResult(intent, MATCH_REGISTER_CODE);
                activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        fab.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fab_scale_in));
    }

    private void setupListView(){
        final ArrayList<Match> matchesList = reverse(realm.where(Match.class).findAll());

        adapter = new MatchesAdapter(matchesList, activity, realm);
        listView = getElementById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                deleteAdapter = new MatchesDeleteAdapter(matchesList, activity, realm, position, ListMatchFragment.this);
                listView.setAdapter(deleteAdapter);
                menuDelete.setVisible(true);
                return true;
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

    private ArrayList<Match> reverse(RealmResults<Match> results){
        ArrayList<Match> arrayList = new ArrayList<>();
        for (int i = results.size(); i > Math.max(results.size() - 20, 0) ; i--) {
            arrayList.add(results.get(i-1));
        }
        return arrayList;
    }
}
