package br.com.extractor.ygops.view.activity.register;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.UUID;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Player;
import br.com.extractor.ygops.util.RealmUtils;
import br.com.extractor.ygops.view.ParentActivity;
import br.com.extractor.ygops.view.adapter.ColorAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayerRegisterActivity extends ParentActivity {

    @Bind(R.id.spnColor) Spinner spnColor;
    @Bind(R.id.edtPlayerName) EditText edtPlayerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.activity_player_register);
        displayHomeEnabled();
        setTitle(getString(R.string.players));
        ButterKnife.bind(this);

        ColorAdapter adapter = new ColorAdapter(this);
        spnColor.setAdapter(adapter);
    }

    @OnClick(R.id.btnDone)
    public void done() {
        if (edtPlayerName.getText() != null && !"".equals(edtPlayerName.getText().toString())) {
            Player player = new Player();
            player.setUuid(UUID.randomUUID().toString());
            player.setNome(edtPlayerName.getText().toString());
            player.setColor(spnColor.getSelectedItemPosition());

            makeToast(R.string.successfully_included, Toast.LENGTH_SHORT);
            RealmUtils.getInstance().insert(player);
            finish();
        } else {
            edtPlayerName.setError(getString(R.string.field_required));
        }
    }

    @OnClick(R.id.btnCancel)
    public void cancel() {
        finish();
    }

}
