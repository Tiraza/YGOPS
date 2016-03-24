package br.com.extractor.ygops.view.activity.register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.UUID;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Deck;
import br.com.extractor.ygops.util.RealmUtils;
import br.com.extractor.ygops.view.ParentActivity;
import br.com.extractor.ygops.view.adapter.ColorAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeckRegisterActivity extends ParentActivity {

    @Bind(R.id.spnColor) Spinner spnColor;
    @Bind(R.id.edtDeckName) EditText edtDeckName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.activity_deck_register);
        displayHomeEnabled();
        setTitle(getString(R.string.decks));
        ButterKnife.bind(this);

        ColorAdapter adapter = new ColorAdapter(this);
        spnColor.setAdapter(adapter);
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

    @OnClick(R.id.btnDone)
    public void done(){
        if (edtDeckName.getText() != null && !"".equals(edtDeckName.getText().toString())) {
            Deck deck = new Deck();
            deck.setUuid(UUID.randomUUID().toString());
            deck.setNome(edtDeckName.getText().toString());
            deck.setColor(spnColor.getSelectedItemPosition());

            makeToast(R.string.successfully_included, Toast.LENGTH_SHORT);
            RealmUtils.getInstance().insert(deck);
            finish();
        } else {
            edtDeckName.setError(getString(R.string.field_required));
        }
    }

    @OnClick(R.id.btnCancel)
    public void cancel(){
        finish();
    }
}
