package br.com.extractor.ygops.view.fragment;

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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Match;
import br.com.extractor.ygops.view.RealmFragment;
import io.realm.RealmResults;

/**
 * Created by Muryllo Tiraza on 11/02/2016.
 */
public class HomeFragment extends RealmFragment {

    private PieChart chart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return onCreateView(inflater, container, R.layout.graph_piechart);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chart = getElementById(R.id.chart);
        setup(chart);
        chart.setCenterText(generateCenterSpannableText());
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    private void setData() {
        RealmResults<Match> result = realm.allObjects(Match.class);
        int wins = result.where().equalTo("winner", true).findAll().size();
        int losers = result.where().equalTo("winner", false).findAll().size();

        ArrayList<Entry> yVal = new ArrayList<>();
        yVal.add(new Entry(wins, 0));
        yVal.add(new Entry(losers, 1));

        ArrayList<String> xVals = new ArrayList<>();
        xVals.add("Wins");
        xVals.add("Losers");

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
        SpannableString s = new SpannableString("Teste\n HUE");
        s.setSpan(new ForegroundColorSpan(Color.rgb(240, 115, 126)), 0, 6, 0);
        s.setSpan(new RelativeSizeSpan(2.2f), 0, 6, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), 7, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 7, s.length(), 0);
        s.setSpan(new RelativeSizeSpan(0.85f), 7, s.length(), 0);
        return s;
    }
}
