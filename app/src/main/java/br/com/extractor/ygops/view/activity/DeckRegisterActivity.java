package br.com.extractor.ygops.view.activity;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Deck;
import br.com.extractor.ygops.util.RealmUtils;
import br.com.extractor.ygops.view.ParentActivity;

public class DeckRegisterActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.activity_deck_register);
        setTitle(getString(R.string.decks));

        Button btnDone = getElementById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edtDeckName = getElementById(R.id.edtDeckName);
                if (edtDeckName.getText() != null && !"".equals(edtDeckName.getText().toString())) {
                    Deck deck = new Deck();
                    deck.setUuid(UUID.randomUUID().toString());
                    deck.setNome(edtDeckName.getText().toString());

                    RealmUtils.insert(deck);
                    finish();
                } else {
                    edtDeckName.setError(getString(R.string.field_required));
                }
            }
        });

        Button btnCancel = getElementById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
