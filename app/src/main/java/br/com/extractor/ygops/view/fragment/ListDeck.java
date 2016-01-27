package br.com.extractor.ygops.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.view.ParentFragment;

/**
 * Created by Muryllo Tiraza on 27/01/2016.
 */
public class ListDeck extends ParentFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return onCreateView(inflater, container, R.layout.fragment_list_deck);
    }
}
