package br.com.extractor.ygops.view.activity;

import android.os.Bundle;
import android.widget.Toast;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.view.ParentActivity;

public class Players extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        makeToast(R.string.players, Toast.LENGTH_LONG);
    }
}
