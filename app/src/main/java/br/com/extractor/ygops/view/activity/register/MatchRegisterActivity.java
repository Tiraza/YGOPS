package br.com.extractor.ygops.view.activity.register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Deck;
import br.com.extractor.ygops.model.ItemAdapter;
import br.com.extractor.ygops.model.Match;
import br.com.extractor.ygops.model.Player;
import br.com.extractor.ygops.model.Profile;
import br.com.extractor.ygops.util.RealmUtils;
import br.com.extractor.ygops.view.ParentActivity;
import br.com.extractor.ygops.view.adapter.CustomSpinnerAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class MatchRegisterActivity extends ParentActivity {

    @Bind(R.id.spnDeck1) Spinner spnDeck1;
    @Bind(R.id.spnDeck2) Spinner spnDeck2;
    @Bind(R.id.spnPlayer) Spinner spnPlayer;
    @Bind(R.id.swtWinner) Switch swtWinner;
    @Bind(R.id.txtDuelist) TextView txtDuelist;

    private Boolean isWinner = false;
    private RealmResults<Deck> listDeck;
    private RealmResults<Player> listPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.activity_match_register);
        displayHomeEnabled();
        ButterKnife.bind(this);

        setupSwitch();
        setupInfoProfile();
        setupSpinnerDeck();
        setupSpinnerPlayers();
    }

    @OnClick(R.id.btnDone)
    public void done(){
        if (validaCampos()) {
            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();
            Player opponent = listPlayer.get(spnPlayer.getSelectedItemPosition() - 1);
            Deck opponentDeck = listDeck.get(spnDeck2.getSelectedItemPosition() - 1);
            realm.commitTransaction();

            realm.close();

            Match match = new Match();
            match.setDate(new Date());
            match.setUuid(UUID.randomUUID().toString());
            match.setDeck(listDeck.get(spnDeck1.getSelectedItemPosition() - 1));
            match.setPlayer(opponent);
            match.setPlayerDeck(opponentDeck);
            match.setWinner(isWinner);

            makeToast(R.string.successfully_included, Toast.LENGTH_SHORT);
            RealmUtils.getInstance().insert(match);
            MatchRegisterActivity.this.finish();
        }
    }

    @OnClick(R.id.btnCancel)
    public void cancel(){
        finish();
    }

    private void setupInfoProfile() {
        Profile profile = RealmUtils.getInstance().get(Profile.class);
        txtDuelist.setText(profile.getNome());
    }

    private void setupSpinnerPlayers() {
        listPlayer = RealmUtils.getInstance().getAll(Player.class);
        listPlayer.sort("nome", Sort.ASCENDING);

        List<ItemAdapter> itens = new ArrayList<>();
        ItemAdapter item = new ItemAdapter(getString(R.string.select_a_player), 0);
        itens.add(item);

        for (Player player : listPlayer) {
            item = new ItemAdapter(player.getNome(), player.getColor());
            itens.add(item);
        }

        CustomSpinnerAdapter adapterPlayer = new CustomSpinnerAdapter(this, itens);
        spnPlayer.setAdapter(adapterPlayer);
    }

    private void setupSpinnerDeck() {
        listDeck = RealmUtils.getInstance().getAll(Deck.class);
        listDeck.sort("nome", Sort.ASCENDING);

        ArrayList<ItemAdapter> itens = new ArrayList<>();
        ItemAdapter item = new ItemAdapter(getString(R.string.select_a_deck), 0);
        itens.add(item);

        for (Deck deck : listDeck) {
            item = new ItemAdapter(deck.getNome(), deck.getColor());
            itens.add(item);
        }

        CustomSpinnerAdapter adapterDeck1 = new CustomSpinnerAdapter(this, itens);
        spnDeck1.setAdapter(adapterDeck1);

        CustomSpinnerAdapter adapterDeck2 = new CustomSpinnerAdapter(this, itens);
        spnDeck2.setAdapter(adapterDeck2);
    }

    private void setupSwitch() {
        swtWinner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                isWinner = isChecked;
            }
        });
    }

    private boolean validaCampos() {
        Boolean isValido = true;

        if (spnPlayer.getSelectedItemPosition() <= 0) {
            isValido = false;
            setErrorSpinner(spnPlayer);
        }

        if (spnDeck1.getSelectedItemPosition() <= 0) {
            isValido = false;
            setErrorSpinner(spnDeck1);
        }

        if (spnDeck2.getSelectedItemPosition() <= 0) {
            isValido = false;
            setErrorSpinner(spnDeck2);
        }

        return isValido;
    }

    private void setErrorSpinner(Spinner spn) {
        CustomSpinnerAdapter adapter = (CustomSpinnerAdapter) spn.getAdapter();
        adapter.setError(spn.getSelectedView());
    }
}
