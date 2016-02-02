package br.com.extractor.ygops.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Player;
import br.com.extractor.ygops.util.RealmUtils;

/**
 * Created by Muryllo Tiraza on 02/02/2016.
 */
public class AddPlayer {

    public AddPlayer (final Context context, final DialogResult result) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add_player);

        Button btnDone = (Button) dialog.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edtPlayerName = (EditText) dialog.findViewById(R.id.edtPlayerName);
                if(edtPlayerName.getText() != null || !"".equals(edtPlayerName.getText().toString())){
                    Player player = new Player();
                    player.setUuid(UUID.randomUUID().toString());
                    player.setNome(edtPlayerName.getText().toString());

                    RealmUtils.insert(player);

                    result.excute();
                    dialog.dismiss();
                } else {
                    edtPlayerName.setError(context.getString(R.string.field_required));
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
