package br.com.extractor.ygops.view;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.analytics.GoogleAnalytics;

import br.com.extractor.ygops.R;
import io.realm.Realm;

/**
 * Created by Muryllo Tiraza on 27/01/2016.
 */
public abstract class ParentActivity extends ActionBarActivity {

    private Toast toast;
    protected Toolbar toolbar;
    protected Realm realm;

    protected void onCreate(Bundle savedInstanceState, int layoutId) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);

        toolbar = getElementById(R.id.tb_main);
        setSupportActionBar(toolbar);
        realm = Realm.getDefaultInstance();
    }

    @Override
    protected void onPause() {
        realm.close();
        super.onPause();
    }

    @Override
    protected void onResume() {
        realm = Realm.getDefaultInstance();
        super.onResume();
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

    protected final <T> T getElementById(int id) {
        return (T) findViewById(id);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
