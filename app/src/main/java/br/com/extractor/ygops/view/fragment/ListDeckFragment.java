package br.com.extractor.ygops.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.lapism.searchview.adapter.SearchAdapter;
import com.lapism.searchview.adapter.SearchItem;
import com.lapism.searchview.history.SearchHistoryTable;
import com.lapism.searchview.view.SearchCodes;
import com.lapism.searchview.view.SearchView;

import java.util.ArrayList;
import java.util.List;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Deck;
import br.com.extractor.ygops.util.RealmUtils;
import br.com.extractor.ygops.view.RealmFragment;
import br.com.extractor.ygops.view.activity.MainActivity;
import br.com.extractor.ygops.view.activity.consult.DeckConsultActivity;
import br.com.extractor.ygops.view.activity.register.DeckRegisterActivity;
import br.com.extractor.ygops.view.adapter.DecksAdapter;
import br.com.extractor.ygops.view.adapter.DecksDeleteAdapter;
import br.com.extractor.ygops.view.interfaces.OnDeleteRealm;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Muryllo Tiraza on 27/01/2016.
 */
public class ListDeckFragment extends RealmFragment implements OnDeleteRealm {

    private SearchView mSearchView;
    private DecksAdapter adapter;
    private DecksDeleteAdapter deleteAdapter;
    private MenuItem menuDelete;

    private SearchHistoryTable mHistoryDatabase;
    private List<SearchItem> mSuggestionsList;

    @Bind(R.id.listView) ListView listView;
    @Bind(R.id.fab) FloatingActionButton fab;

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

        ButterKnife.bind(this, view);

        setupFab();
        setupListView();
        setupSearchView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        menuDelete = menu.findItem(R.id.menuDelete);
        menuDelete.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuDelete:
                for (String uuid : deleteAdapter.getSelectedItens()) {
                    RealmUtils.getInstance().removeDeckForUuid(uuid);
                }

                listView.setAdapter(adapter);
                break;

            case R.id.action_search:
                mSuggestionsList.clear();
                mSuggestionsList.addAll(mHistoryDatabase.getAllItems());
                mSearchView.setVisibility(View.VISIBLE);
                mSearchView.show(true);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDelete() {
        listView.setAdapter(adapter);
        menuDelete.setVisible(false);
    }

    private void setupFab() {
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
        final RealmResults<Deck> decks = RealmUtils.getInstance().getAll(Deck.class);
        if (!decks.isEmpty()) {
            decks.sort("nome", Sort.ASCENDING);
        }
        adapter = new DecksAdapter(activity, decks, true);

        listView.setAdapter(adapter);
        listView.setEmptyView((View) getElementById(R.id.empty_list_view));
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                deleteAdapter = new DecksDeleteAdapter(decks, activity, ListDeckFragment.this, decks.get(position).getUuid());
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
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

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

    private void setupSearchView(){
        mSuggestionsList = new ArrayList<>();
        mHistoryDatabase = new SearchHistoryTable(activity);

        mSearchView = ((MainActivity) activity).getSearchView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchView.hide(false);
                mHistoryDatabase.addItem(new SearchItem(query));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mSearchView.setOnSearchViewListener(new SearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                fab.hide(false);
            }

            @Override
            public void onSearchViewClosed() {
                fab.show(false);
            }
        });

        List<SearchItem> mResultsList = new ArrayList<>();
        SearchAdapter mSearchAdapter = new SearchAdapter(activity, mResultsList, mSuggestionsList, SearchCodes.THEME_LIGHT);
        mSearchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mSearchView.hide(false);
                TextView textView = (TextView) view.findViewById(R.id.textView_item_text);
                CharSequence text = textView.getText();
                mHistoryDatabase.addItem(new SearchItem(text));
            }
        });

        mSearchView.setAdapter(mSearchAdapter);
    }
}
