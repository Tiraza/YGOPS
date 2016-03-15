package br.com.extractor.ygops.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Match;
import br.com.extractor.ygops.view.RealmFragment;
import br.com.extractor.ygops.view.activity.MainActivity;
import br.com.extractor.ygops.view.activity.register.DeckRegisterActivity;
import br.com.extractor.ygops.view.activity.register.MatchRegisterActivity;
import br.com.extractor.ygops.view.activity.register.PlayerRegisterActivity;
import io.realm.RealmResults;

/**
 * Created by Muryllo Tiraza on 11/02/2016.
 */
public class HomeFragment extends RealmFragment {

    private FloatingActionMenu fabMenu;
    private PieChart chart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return onCreateView(inflater, container, R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)activity).toggleIconToolbar(false);
        activity.setTitle(R.string.home);
        setupActionMenu();

        TextView txtGraphName = getElementById(R.id.txtGraphName);
        txtGraphName.setText(R.string.wins_losses);

        chart = getElementById(R.id.chart);
        setup(chart);
        chart.setCenterText(generateCenterSpannableText());
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
        fabMenu.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fab_scale_in));
    }

    private void setupActionMenu(){
        fabMenu = getElementById(R.id.fab_menu);
        fabMenu.setMenuButtonColorNormalResId(R.color.primary);
        fabMenu.setMenuButtonColorPressedResId(R.color.accent);

        FloatingActionButton fabDeck = getElementById(R.id.fabDeck);
        fabDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegisterActivity(DeckRegisterActivity.class);
            }
        });

        FloatingActionButton fabPlayer = getElementById(R.id.fabPlayer);
        fabPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegisterActivity(PlayerRegisterActivity.class);
            }
        });

        FloatingActionButton fabMatch = getElementById(R.id.fabMatch);
        fabMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegisterActivity(MatchRegisterActivity.class);
            }
        });
    }

    private void startRegisterActivity(Class classe){
        if(fabMenu.isOpened()) {
            fabMenu.close(false);
            Intent intent = new Intent(activity, classe);
            startActivity(intent);
            activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
    }

    private void setData() {
        RealmResults<Match> result = realm.allObjects(Match.class);
        int totalMatches = result.size();
        int wins = result.where().equalTo("winner", true).findAll().size();
        int losses = result.where().equalTo("winner", false).findAll().size();

        float winsPercent = (wins * 100.0f) / totalMatches;
        float lossesPercent = (losses * 100.0f) / totalMatches;

        ArrayList<Entry> yVal = new ArrayList<>();
        yVal.add(new Entry(winsPercent, 0));
        yVal.add(new Entry(lossesPercent, 1));

        ArrayList<String> xVals = new ArrayList<>();
        xVals.add(getString(R.string.wins));
        xVals.add(getString(R.string.losses));

        PieDataSet dataSet = new PieDataSet(yVal, "");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);

        int[] colors = {getResources().getColor(R.color.match_winner), getResources().getColor(R.color.match_loser)};
        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        chart.setData(data);
        chart.animateY(1400);
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
