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
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Deck;
import br.com.extractor.ygops.model.Match;
import br.com.extractor.ygops.model.Player;
import br.com.extractor.ygops.view.RealmFragment;
import br.com.extractor.ygops.view.activity.consult.DeckConsultActivity;
import br.com.extractor.ygops.view.activity.consult.PlayerConsultActivity;
import br.com.extractor.ygops.view.activity.register.PlayerRegisterActivity;
import br.com.extractor.ygops.view.adapter.PlayersAdapter;
import br.com.extractor.ygops.view.adapter.PlayersDeleteAdapter;
import br.com.extractor.ygops.view.interfaces.DeleteAdapter;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Muryllo Tiraza on 02/02/2016.
 */
public class ListPlayerFragment extends RealmFragment implements DeleteAdapter {


    private PlayersDeleteAdapter deleteAdapter;
    private MenuItem menuDelete;
    private ListView listView;
    private PlayersAdapter adapter;

    private static int PLAYER_REGISTER_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return onCreateView(inflater, container, R.layout.fragment_list);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        activity.setTitle(R.string.players);

        final RealmResults<Player> players = realm.where(Player.class).findAll();
        players.sort("nome", Sort.ASCENDING);

        adapter = new PlayersAdapter(players, activity);

        listView = getElementById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                deleteAdapter = new PlayersDeleteAdapter(players, activity, position, ListPlayerFragment.this);
                listView.setAdapter(deleteAdapter);
                menuDelete.setVisible(true);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Player player = adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("playerUuid", player.getUuid());
                Intent intent = new Intent(activity, PlayerConsultActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

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
            RealmQuery<Match> queryMatch = realm.where(Match.class);
            queryMatch.equalTo("player.nome", "");

            for(Player player : deleteAdapter.getSelectedItens()){
                queryMatch.or().equalTo("player.nome", player.getNome());
            }

            if(queryMatch.findAll().isEmpty()) {
                realm.beginTransaction();
                RealmQuery<Player> query = realm.where(Player.class);
                query.equalTo("nome", "");

                for (Player player : deleteAdapter.getSelectedItens()) {
                    query.or().equalTo("nome", player.getNome());
                }

                query.findAll().clear();
                realm.commitTransaction();

                RealmResults<Player> players = realm.where(Player.class).findAll();
                players.sort("nome", Sort.ASCENDING);
                adapter = new PlayersAdapter(players, activity);
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
}
