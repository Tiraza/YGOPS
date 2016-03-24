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
import br.com.extractor.ygops.util.RealmUtils;
import br.com.extractor.ygops.view.ParentActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * Created by Muryllo Tiraza on 10/02/2016.
 */
public class DeckConsultActivity extends ParentActivity {

    @Bind(R.id.edtDeckName)
    EditText edtDeckName;
    @Bind(R.id.image_view)
    ImageView img;
    @Bind(R.id.txtTotal)
    TextView txtTotal;
    @Bind(R.id.txtWins)
    TextView txtWins;
    @Bind(R.id.txtLosses)
    TextView txtLosses;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.activity_deck_consult);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        displayHomeEnabled();

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String deckName = bundle.getString("deckName");
            Deck deck = RealmUtils.getInstance().getForName(Deck.class, deckName);
            setupCardDeck(deck);
            setupCardInfo(deck);
        }
    }

    private void setupCardDeck(Deck deck) {
        edtDeckName.setText(deck.getNome());
        img.setImageDrawable(ImageUtils.getInstance().getDrawableRealm(R.string.empty, deck.getColor(), this));
    }

    private void setupCardInfo(Deck deck) {
        Realm realm = Realm.getDefaultInstance();

        RealmQuery<Match> query = realm.where(Match.class);
        query.equalTo("deck.nome", deck.getNome()).or().equalTo("playerDeck.nome", deck.getNome());

        int total = query.findAll().size();
        txtTotal.setText("" + total);

        int wins = query.findAll().where().equalTo("winner", true).findAll().size();
        txtWins.setText("" + wins);

        Integer losses = total - wins;
        txtLosses.setText(losses.toString());

        realm.close();
    }
}
