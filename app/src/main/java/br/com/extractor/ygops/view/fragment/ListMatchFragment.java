package br.com.extractor.ygops.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Player;
import br.com.extractor.ygops.view.RealmFragment;
import br.com.extractor.ygops.view.activity.MatchRegisterActivity;
import br.com.extractor.ygops.view.interfaces.DeleteAdapter;
import io.realm.RealmResults;

/**
 * Created by Muryllo Tiraza on 05/02/2016.
 */
public class ListMatchFragment extends RealmFragment implements DeleteAdapter {

    private static int MATCH_REGISTER_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return onCreateView(inflater, container, R.layout.fragment_list);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = getElementById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, MatchRegisterActivity.class);
                startActivityForResult(intent, MATCH_REGISTER_CODE);
                activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
        fab.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fab_scale_in));
    }

    @Override
    public void onDelete() {

    }

}
