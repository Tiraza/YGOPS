package br.com.extractor.ygops.view.activity.register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.UUID;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Player;
import br.com.extractor.ygops.util.RealmUtils;
import br.com.extractor.ygops.view.ParentActivity;
import br.com.extractor.ygops.view.adapter.ColorAdapter;

public class PlayerRegisterActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.activity_player_register);
        setTitle(getString(R.string.players));

        ColorAdapter adapter = new ColorAdapter(this);
        final Spinner spnColor = getElementById(R.id.spnColor);
        spnColor.setAdapter(adapter);

        Button btnDone = getElementById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edtPlayerName = getElementById(R.id.edtPlayerName);
                if (edtPlayerName.getText() != null && !"".equals(edtPlayerName.getText().toString())) {
                    Player player = new Player();
                    player.setUuid(UUID.randomUUID().toString());
                    player.setNome(edtPlayerName.getText().toString());
                    player.setColor(spnColor.getSelectedItemPosition());

                    makeToast(R.string.successfully_included, Toast.LENGTH_LONG);
                    RealmUtils.insert(player);
                    finish();
                } else {
                    edtPlayerName.setError(getString(R.string.field_required));
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
