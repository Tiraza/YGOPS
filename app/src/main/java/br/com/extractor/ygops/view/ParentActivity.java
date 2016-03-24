package br.com.extractor.ygops.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.analytics.GoogleAnalytics;

import br.com.extractor.ygops.R;

/**
 * Created by Muryllo Tiraza on 27/01/2016.
 */
public abstract class ParentActivity extends AppCompatActivity {

    private Toast toast;
    protected Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState, int layoutId) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);

        toolbar = (Toolbar) findViewById(R.id.tb_main);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

    protected final void makeToast(int idMensagem, int duracao) {
        makeToast(getString(idMensagem), duracao);
    }

    protected final void makeToast(String mensagem, int duracao) {
        if (toast != null)
            toast.cancel();

        toast = Toast.makeText(this, mensagem, duracao);
        toast.setText(mensagem);
        toast.setDuration(duracao);
        toast.show();
    }

    protected final void displayHomeEnabled() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }
}
