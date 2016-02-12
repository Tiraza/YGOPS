package br.com.extractor.ygops.view.activity.consult;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Deck;
import br.com.extractor.ygops.model.Match;
import br.com.extractor.ygops.model.Player;
import br.com.extractor.ygops.util.ImageUtils;
import br.com.extractor.ygops.util.MapUtils;
import br.com.extractor.ygops.view.ParentActivity;
import io.realm.RealmQuery;

/**
 * Created by Muryllo Tiraza on 11/02/2016.
 */
public class PlayerConsultActivity extends ParentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.activity_player_consult);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String playerUuid = bundle.getString("playerUuid");
            RealmQuery<Player> query = realm.where(Player.class);
            query.equalTo("uuid", playerUuid);

            Player player = query.findFirst();
            setupCardDeck(player);
            setupCardInfo(player);
            setupCardMoreUsedDecks(player);
        }
    }

    private void setupCardDeck(Player player) {
        EditText edtDeckName = getElementById(R.id.edtPlayerName);
        edtDeckName.setText(player.getNome());

        ImageView img = getElementById(R.id.image_view);
        img.setImageDrawable(ImageUtils.getDrawableRealm(R.string.empty, player.getColor(), this));
    }

    private void setupCardInfo(Player player) {
        RealmQuery<Match> query = realm.where(Match.class);
        query.equalTo("player.uuid", player.getUuid());

        int total = query.findAll().size();
        TextView txtTotal = getElementById(R.id.txtTotal);
        txtTotal.setText("" + total);

        int wins = query.findAll().where().equalTo("winner", false).findAll().size();
        TextView txtWins = getElementById(R.id.txtWins);
        txtWins.setText("" + wins);

        Integer losses = total - wins;
        TextView txtLosses = getElementById(R.id.txtLosses);
        txtLosses.setText(losses.toString());
    }

    private void setupCardMoreUsedDecks(Player player) {
        HashMap<String, Integer> sortedMap = getSortedValues(player);

        TextView txtDeck1 = getElementById(R.id.txtDeck1);
        TextView txtTotalDeck1 = getElementById(R.id.txtTotalDeck1);
        TextView txtDeck2 = getElementById(R.id.txtDeck2);
        TextView txtTotalDeck2 = getElementById(R.id.txtTotalDeck2);
        TextView txtDeck3 = getElementById(R.id.txtDeck3);
        TextView txtTotalDeck3 = getElementById(R.id.txtTotalDeck3);

        if (!sortedMap.isEmpty()) {
            Map.Entry<String, Integer> value = sortedMap.entrySet().iterator().next();
            txtDeck1.setText(value.getKey());
            txtTotalDeck1.setText(value.getValue().toString());
            sortedMap.remove(value.getKey());

            if (!sortedMap.isEmpty()) {
                value = sortedMap.entrySet().iterator().next();
                txtDeck2.setText(value.getKey());
                txtTotalDeck2.setText(value.getValue().toString());
                sortedMap.remove(value.getKey());

                if (!sortedMap.isEmpty()) {
                    value = sortedMap.entrySet().iterator().next();
                    txtDeck3.setText(value.getKey());
                    txtTotalDeck3.setText(value.getValue().toString());
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
            CardView cardView = getElementById(R.id.cv_more_used_decks);
            cardView.setVisibility(View.GONE);
        }
    }

    public HashMap<String, Integer> getSortedValues(Player player) {
        List<Deck> decks = player.getDecks();

        HashMap<String, Integer> map = new HashMap<>();
        HashSet<Deck> set = new HashSet<>(decks);

        for (Deck deck : set) {
            map.put(deck.getNome(), Collections.frequency(decks, deck));
        }

        return MapUtils.sortByValue(map);
    }
}
