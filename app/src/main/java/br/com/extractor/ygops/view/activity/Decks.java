package br.com.extractor.ygops.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.view.ParentActivity;
import br.com.extractor.ygops.view.fragment.ListDeck;

public class Decks extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decks);

        ListDeck fragment = new ListDeck();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
