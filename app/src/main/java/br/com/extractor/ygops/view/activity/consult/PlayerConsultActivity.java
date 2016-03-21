package br.com.extractor.ygops.view.activity.consult;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Deck;
import br.com.extractor.ygops.model.ItemCount;
import br.com.extractor.ygops.model.Match;
import br.com.extractor.ygops.model.Player;
import br.com.extractor.ygops.util.ImageUtils;
import br.com.extractor.ygops.util.MapUtils;
import br.com.extractor.ygops.util.RealmUtils;
import br.com.extractor.ygops.view.ParentActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmQuery;

/**
 * Created by Muryllo Tiraza on 11/02/2016.
 */
public class PlayerConsultActivity extends ParentActivity {

    @Bind(R.id.image_view)
    ImageView img;
    @Bind(R.id.edtPlayerName)
    EditText edtDeckName;

    @Bind(R.id.txtWins)
    TextView txtWins;
    @Bind(R.id.txtTotal)
    TextView txtTotal;
    @Bind(R.id.txtLosses)
    TextView txtLosses;

    @Bind(R.id.txtDeck1)
    TextView txtDeck1;
    @Bind(R.id.txtTotalDeck1)
    TextView txtTotalDeck1;
    @Bind(R.id.txtDeck2)
    TextView txtDeck2;
    @Bind(R.id.txtTotalDeck2)
    TextView txtTotalDeck2;
    @Bind(R.id.txtDeck3)
    TextView txtDeck3;
    @Bind(R.id.txtTotalDeck3)
    TextView txtTotalDeck3;
    @Bind(R.id.cv_more_used_decks)
    CardView cardView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.activity_player_consult);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        displayHomeEnabled();
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String playerUuid = bundle.getString("playerUuid");
            Player player = RealmUtils.getInstance().get(Player.class, playerUuid);
            setupPlayerInfo(player);
            setupCardInfo(player);
            setupCardMoreUsedDecks(player);
        }
    }

    private void setupPlayerInfo(Player player) {
        edtDeckName.setText(player.getNome());
        img.setImageDrawable(ImageUtils.getInstance().getDrawableRealm(R.string.empty, player.getColor(), this));
    }

    private void setupCardInfo(Player player) {
        RealmQuery<Match> query = realm.where(Match.class);
        query.equalTo("player.uuid", player.getUuid());

        int total = query.findAll().size();
        txtTotal.setText("" + total);

        int wins = query.findAll().where().equalTo("winner", false).findAll().size();
        txtWins.setText("" + wins);

        Integer losses = total - wins;
        txtLosses.setText(losses.toString());
    }

    private void setupCardMoreUsedDecks(Player player) {
        List<ItemCount> sortedList = getSortedValues(player);

        if (!sortedList.isEmpty()) {
            ItemCount item = sortedList.get(0);
            txtDeck1.setText(item.getNome());
            txtTotalDeck1.setText(item.getQuantidade().toString());
            sortedList.remove(item);

            if (!sortedList.isEmpty()) {
                item = sortedList.get(0);
                txtDeck2.setText(item.getNome());
                txtTotalDeck2.setText(item.getQuantidade().toString());
                sortedList.remove(item);

                if (!sortedList.isEmpty()) {
                    item = sortedList.get(0);
                    txtDeck3.setText(item.getNome());
                    txtTotalDeck3.setText(item.getQuantidade().toString());
                } else {
                    txtDeck3.setVisibility(View.GONE);
                    txtTotalDeck3.setVisibility(View.GONE);
                }
            } else {
                txtDeck2.setVisibility(View.GONE);
                txtTotalDeck2.setVisibility(View.GONE);
                txtDeck3.setVisibility(View.GONE);
                txtTotalDeck3.setVisibility(View.GONE);
            }
        } else {
            cardView.setVisibility(View.GONE);
        }
    }

    public List<ItemCount> getSortedValues(Player player) {
        List<Deck> decks = player.getDecks();

        HashMap<String, Integer> map = new HashMap<>();
        HashSet<Deck> set = new HashSet<>(decks);

        for (Deck deck : set) {
            map.put(deck.getNome(), Collections.frequency(decks, deck));
        }

        return MapUtils.sortByValue(map);
    }
}
