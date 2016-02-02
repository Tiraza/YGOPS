package br.com.extractor.ygops.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Owner;
import br.com.extractor.ygops.util.RealmUtils;

/**
 * Created by Muryllo Tiraza on 02/02/2016.
 */
public class DialogRegister {

    public DialogRegister(final Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_register);

        Button btnDone = (Button) dialog.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edtName = (EditText) dialog.findViewById(R.id.edtPlayerName);
                if(edtName.getText() != null || !"".equals(edtName.getText().toString())){
                    Owner owner = new Owner();
                    owner.setUuid(UUID.randomUUID().toString());
                    owner.setNome(edtName.getText().toString());

                    RealmUtils.insert(owner);

                    dialog.dismiss();
                } else {
                    edtName.setError(context.getString(R.string.field_required));
                }
            }
        });
    }
}
