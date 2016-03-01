package br.com.extractor.ygops.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.util.CustomAnimations;
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
        ((MainActivity) activity).toggleIconToolbar(false);

        setupTextView();
        setupButtons();
    }

    private void setupTextView(){
        CustomAnimations animation = CustomAnimations.getInstance();

        TextView txtLifePlayer = getElementById(R.id.txtLifePlayer);
        //animation.animateTextView(0, 8000, txtLifePlayer);

        TextView txtOponnentPlayer = getElementById(R.id.txtLifeOpponent);
        //animation.animateTextView(0, 8000, txtOponnentPlayer);
    }

    private void setupButtons() {
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.fab_scale_in);

        ImageButton btnPlayerAdd = getElementById(R.id.btnPlayerAdd);
        btnPlayerAdd.setColorFilter(getResources().getColor(R.color.match_winner));
        btnPlayerAdd.startAnimation(animation);

        ImageButton btnPlayerRemove = getElementById(R.id.btnPlayerRemove);
        btnPlayerRemove.setColorFilter(getResources().getColor(R.color.match_loser));
        btnPlayerRemove.startAnimation(animation);

        ImageButton btnPlayerHalf = getElementById(R.id.btnPlayerHalf);
        btnPlayerHalf.startAnimation(animation);

        ImageButton btnOpponentAdd = getElementById(R.id.btnOponnentAdd);
        btnOpponentAdd.setColorFilter(getResources().getColor(R.color.match_winner));
        btnOpponentAdd.startAnimation(animation);

        ImageButton btnOponnentRemove = getElementById(R.id.btnOponnentRemove);
        btnOponnentRemove.setColorFilter(getResources().getColor(R.color.match_loser));
        btnOponnentRemove.startAnimation(animation);

        ImageButton btnOponnentHalf = getElementById(R.id.btnOponnentHalf);
        btnOponnentHalf.startAnimation(animation);
    }

}
