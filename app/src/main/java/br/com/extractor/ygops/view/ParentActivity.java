package br.com.extractor.ygops.view;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import br.com.extractor.ygops.R;

/**
 * Created by Muryllo Tiraza on 27/01/2016.
 */
public abstract class ParentActivity  extends ActionBarActivity  {
    private Toast toast;
    protected Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState, int layoutId) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);

        toolbar = getElementById(R.id.tb_main);
        setSupportActionBar(toolbar);
    }

    protected final <T> T getElementById(int id){
        return (T)findViewById(id);
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
}
