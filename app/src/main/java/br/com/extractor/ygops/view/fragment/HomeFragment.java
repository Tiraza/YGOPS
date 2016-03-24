package br.com.extractor.ygops.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Match;
import br.com.extractor.ygops.util.RealmUtils;
import br.com.extractor.ygops.view.RealmFragment;
import br.com.extractor.ygops.view.activity.MainActivity;
import br.com.extractor.ygops.view.activity.register.DeckRegisterActivity;
import br.com.extractor.ygops.view.activity.register.MatchRegisterActivity;
import br.com.extractor.ygops.view.activity.register.PlayerRegisterActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Muryllo Tiraza on 11/02/2016.
 */
public class HomeFragment extends RealmFragment {

    @Bind(R.id.chart) PieChart chart;
    @Bind(R.id.txtWins) TextView txtWins;
    @Bind(R.id.txtTotal) TextView txtTotal;
    @Bind(R.id.txtLosses) TextView txtLosses;
    @Bind(R.id.txtGraphName) TextView txtGraphName;
    @Bind(R.id.fab_menu) FloatingActionMenu fabMenu;
    @Bind(R.id.fabDeck) FloatingActionButton fabDeck;
    @Bind(R.id.fabMatch) FloatingActionButton fabMatch;
    @Bind(R.id.fabPlayer) FloatingActionButton fabPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return onCreateView(inflater, container, R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) activity).toggleIconToolbar(false);
        activity.setTitle(R.string.home);
        ButterKnife.bind(this, view);

        setupActionMenu();
        setupCardInfo();

        setup(chart);
        chart.setCenterText(generateCenterSpannableText());
        fabMenu.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fab_scale_in));
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    private void setupActionMenu() {
        fabMenu.setMenuButtonColorNormalResId(R.color.primary);
        fabMenu.setMenuButtonColorPressedResId(R.color.accent);

        fabDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegisterActivity(DeckRegisterActivity.class);
            }
        });

        fabPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegisterActivity(PlayerRegisterActivity.class);
            }
        });

        fabMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegisterActivity(MatchRegisterActivity.class);
            }
        });
    }

    private void setupCardInfo() {
        RealmResults<Match> results = RealmUtils.getInstance().getAll(Match.class);

        int total = results.size();
        txtTotal.setText("" + total);

        int wins = results.where().equalTo("winner", true).findAll().size();
        txtWins.setText("" + wins);

        Integer losses = total - wins;
        txtLosses.setText(losses.toString());
    }

    private void startRegisterActivity(Class classe) {
        if (fabMenu.isOpened()) {
            fabMenu.close(false);
            Intent intent = new Intent(activity, classe);
            startActivity(intent);
            activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
    }

    private void setData() {
        txtGraphName.setText(R.string.wins_losses);

        RealmResults<Match> result = RealmUtils.getInstance().getAll(Match.class);
        int totalMatches = result.size();
        int wins = result.where().equalTo("winner", true).findAll().size();
        int losses = result.where().equalTo("winner", false).findAll().size();

        float winsPercent = (wins * 100.0f) / totalMatches;
        float lossesPercent = (losses * 100.0f) / totalMatches;

        ArrayList<Entry> yVal = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        if (winsPercent != 0) {
            yVal.add(new Entry(winsPercent, 0));
            xVals.add(getString(R.string.wins));
            colors.add(ContextCompat.getColor(activity, R.color.match_winner));
        }

        if (lossesPercent != 0) {
            yVal.add(new Entry(lossesPercent, 1));
            xVals.add(getString(R.string.losses));
            colors.add(ContextCompat.getColor(activity, R.color.match_loser));
        }

        PieDataSet dataSet = new PieDataSet(yVal, "");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        chart.setData(data);
        chart.animateY(1000);
    }

    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("         ");
        s.setSpan(new ForegroundColorSpan(Color.rgb(240, 115, 126)), 0, 6, 0);
        s.setSpan(new RelativeSizeSpan(2.2f), 0, 6, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), 7, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 7, s.length(), 0);
        s.setSpan(new RelativeSizeSpan(0.85f), 7, s.length(), 0);
        return s;
    }
}
