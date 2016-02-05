package br.com.extractor.ygops.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Deck;
import br.com.extractor.ygops.util.RealmUtils;
import br.com.extractor.ygops.view.ParentActivity;
import br.com.extractor.ygops.view.adapter.ColorAdapter;

public class DeckRegisterActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.activity_deck_register);
        setTitle(getString(R.string.decks));

        ColorAdapter adapter = new ColorAdapter(this);
        final Spinner spnColor = getElementById(R.id.spnColor);
        spnColor.setAdapter(adapter);

        Button btnDone = getElementById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edtDeckName = getElementById(R.id.edtDeckName);
                if (edtDeckName.getText() != null && !"".equals(edtDeckName.getText().toString())) {
                    Deck deck = new Deck();
                    deck.setNome(edtDeckName.getText().toString());
                    deck.setColor(spnColor.getSelectedItemPosition());

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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
