package br.com.extractor.ygops.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.view.ParentFragment;
import br.com.extractor.ygops.view.activity.MainActivity;

/**
 * Created by Muryllo Tiraza on 29/02/2016.
 */
public class CalcFragment extends ParentFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return onCreateView(inflater, container, R.layout.fragment_calc);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity.setTitle(R.string.calculator);
        ((MainActivity)activity).toggleIconToolbar(false);
    }

}
