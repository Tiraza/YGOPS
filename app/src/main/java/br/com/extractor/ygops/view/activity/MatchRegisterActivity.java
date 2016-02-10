package br.com.extractor.ygops.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

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
import io.realm.RealmResults;
import io.realm.Sort;

public class MatchRegisterActivity extends ParentActivity {

    private Spinner spnDeck1;
    private Spinner spnDeck2;
    private Spinner spnPlayer;
    private Boolean isWinner;

    private RealmResults<Player> listPlayer;
    private RealmResults<Deck> listDeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.activity_match_register);

        setupSwitch();
        setupButtons();
        setupInfoProfile();
        setupSpinnerDeck();
        setupSpinnerPlayers();
    }

    private void setupButtons() {
        Button btnCancelar = getElementById(R.id.btnCancel);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MatchRegisterActivity.this.finish();
            }
        });

        Button btnDone = getElementById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validaCampos()) {
                    Match match = new Match();
                    match.setDate(new Date());
                    match.setUuid(UUID.randomUUID().toString());
                    match.setDeck(listDeck.get(spnDeck1.getSelectedItemPosition() - 1));
                    match.setPlayer(listPlayer.get(spnPlayer.getSelectedItemPosition() - 1));
                    match.setPlayerDeck(listDeck.get(spnDeck2.getSelectedItemPosition() - 1));
                    match.setWinner(isWinner);

                    RealmUtils.insert(match);
                    MatchRegisterActivity.this.finish();
                }
            }
        });
    }

    private void setupInfoProfile() {
        Profile profile = realm.where(Profile.class).findFirst();
        TextView txtDuelist = getElementById(R.id.txtDuelist);
        txtDuelist.setText(profile.getNome());
    }

    private void setupSpinnerPlayers() {
        listPlayer = realm.where(Player.class).findAll();
        listPlayer.sort("nome", Sort.ASCENDING);

        List<ItemAdapter> itens = new ArrayList<>();
        ItemAdapter item = new ItemAdapter(getString(R.string.select_a_player), 0);
        itens.add(item);

        for (Player player : listPlayer) {
            item = new ItemAdapter(player.getNome(), player.getColor());
            itens.add(item);
        }

        CustomSpinnerAdapter adapterPlayer = new CustomSpinnerAdapter(this, itens);
        spnPlayer = getElementById(R.id.spnPlayer);
        spnPlayer.setAdapter(adapterPlayer);
    }

    private void setupSpinnerDeck() {
        listDeck = realm.where(Deck.class).findAll();
        listDeck.sort("nome", Sort.ASCENDING);

        ArrayList<ItemAdapter> itens = new ArrayList<>();
        ItemAdapter item = new ItemAdapter(getString(R.string.select_a_deck), 0);
        itens.add(item);

        for (Deck deck : listDeck) {
            item = new ItemAdapter(deck.getNome(), deck.getColor());
            itens.add(item);
        }

        CustomSpinnerAdapter adapterDeck1 = new CustomSpinnerAdapter(this, itens);
        spnDeck1 = getElementById(R.id.spnDeck1);
        spnDeck1.setAdapter(adapterDeck1);

        CustomSpinnerAdapter adapterDeck2 = new CustomSpinnerAdapter(this, itens);
        spnDeck2 = getElementById(R.id.spnDeck2);
        spnDeck2.setAdapter(adapterDeck2);
    }

    private void setupSwitch() {
        Switch swtWinner = getElementById(R.id.swtWinner);
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
