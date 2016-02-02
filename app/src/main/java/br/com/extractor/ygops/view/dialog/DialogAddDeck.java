package br.com.extractor.ygops.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Deck;
import br.com.extractor.ygops.util.RealmUtils;

/**
 * Created by Muryllo Tiraza on 29/01/2016.
 */
public class DialogAddDeck {

    public DialogAddDeck(final Context context, final DialogResult result) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add_deck);

        Button btnDone = (Button) dialog.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edtDeckName = (EditText) dialog.findViewById(R.id.edtDeckName);
                if(edtDeckName.getText() != null || !"".equals(edtDeckName.getText().toString())){
                    Deck deck = new Deck();
                    deck.setUuid(UUID.randomUUID().toString());
                    deck.setNome(edtDeckName.getText().toString());

                    RealmUtils.insert(deck);

                    result.excute();
                    dialog.dismiss();
                } else {
                    edtDeckName.setError(context.getString(R.string.field_required));
                }
            }
        });

        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
