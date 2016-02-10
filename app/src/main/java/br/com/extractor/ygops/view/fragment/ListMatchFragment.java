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
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionButton;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Match;
import br.com.extractor.ygops.view.RealmFragment;
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

    private static int MATCH_REGISTER_CODE = 1;
    private MatchesAdapter adapter;
    private MatchesDeleteAdapter deleteAdapter;
    private MenuItem menuDelete;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return onCreateView(inflater, container, R.layout.fragment_list);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        activity.setTitle(R.string.matches);

        final RealmResults<Match> matches = realm.where(Match.class).findAll();
        matches.sort("date", Sort.ASCENDING);

        adapter = new MatchesAdapter(matches, activity, realm);
        listView = getElementById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                deleteAdapter = new MatchesDeleteAdapter(matches, activity, realm, position, ListMatchFragment.this);
                listView.setAdapter(deleteAdapter);
                menuDelete.setVisible(true);
                return false;
            }
        });


        FloatingActionButton fab = getElementById(R.id.fab);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MATCH_REGISTER_CODE) {
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
        if(item.getItemId() == R.id.menuDelete){
            realm.beginTransaction();
            RealmQuery<Match> query = realm.where(Match.class);
            query.equalTo("uuid", "");

            for(Match player : deleteAdapter.getSelectedItens()){
                query.or().equalTo("uuid", player.getUuid());
            }

            query.findAll().clear();
            realm.commitTransaction();

            RealmResults<Match> matches = realm.where(Match.class).findAll();
            matches.sort("date", Sort.ASCENDING);
            adapter = new MatchesAdapter(matches, activity, realm);
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

}
