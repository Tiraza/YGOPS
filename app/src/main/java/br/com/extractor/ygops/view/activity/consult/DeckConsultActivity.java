package br.com.extractor.ygops.view.activity.consult;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Deck;
import br.com.extractor.ygops.model.Match;
import br.com.extractor.ygops.util.ImageUtils;
import br.com.extractor.ygops.view.ParentActivity;
import io.realm.RealmQuery;

/**
 * Created by Muryllo Tiraza on 10/02/2016.
 */
public class DeckConsultActivity extends ParentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.activity_deck_consult);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        displayHomeEnabled();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String deckName = bundle.getString("deckName");
            RealmQuery<Deck> query = realm.where(Deck.class);
            query.equalTo("nome", deckName);

            Deck deck = query.findFirst();
            setupCardDeck(deck);
            setupCardInfo(deck);
        }
    }

    private void setupCardDeck(Deck deck) {
        EditText edtDeckName = getElementById(R.id.edtDeckName);
        edtDeckName.setText(deck.getNome());

        ImageView img = getElementById(R.id.image_view);
        img.setImageDrawable(ImageUtils.getInstance().getDrawableRealm(R.string.empty, deck.getColor(), this));
    }

    private void setupCardInfo(Deck deck) {
        RealmQuery<Match> query = realm.where(Match.class);
        query.equalTo("deck.nome", deck.getNome()).or().equalTo("playerDeck.nome", deck.getNome());

        int total = query.findAll().size();
        TextView txtTotal = getElementById(R.id.txtTotal);
        txtTotal.setText("" + total);

        int wins = query.findAll().where().equalTo("winner", true).findAll().size();
        TextView txtWins = getElementById(R.id.txtWins);
        txtWins.setText("" + wins);

        Integer losses = total - wins;
        TextView txtLosses = getElementById(R.id.txtLosses);
        txtLosses.setText(losses.toString());
    }
}
