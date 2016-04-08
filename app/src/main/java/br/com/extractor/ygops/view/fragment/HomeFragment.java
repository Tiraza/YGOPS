package br.com.extractor.ygops.view.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Deck;
import br.com.extractor.ygops.model.ItemCount;
import br.com.extractor.ygops.model.Match;
import br.com.extractor.ygops.util.MapUtils;
import br.com.extractor.ygops.util.RealmUtils;
import br.com.extractor.ygops.view.RealmFragment;
import br.com.extractor.ygops.view.activity.MainActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Muryllo Tiraza on 11/02/2016.
 */
public class HomeFragment extends RealmFragment {

    @Bind(R.id.chart) PieChart chart;
    @Bind(R.id.txtGraphName) TextView txtGraphName;
    @Bind(R.id.graph_wins) CardView cardWins;

    @Bind(R.id.txtDeck1) TextView txtDeck1;
    @Bind(R.id.txtTotalDeck1) TextView txtTotalDeck1;
    @Bind(R.id.txtDeck2) TextView txtDeck2;
    @Bind(R.id.txtTotalDeck2) TextView txtTotalDeck2;
    @Bind(R.id.txtDeck3) TextView txtDeck3;
    @Bind(R.id.txtTotalDeck3) TextView txtTotalDeck3;
    @Bind(R.id.used_decks) CardView cardMoreUsedDecks;

    @Bind(R.id.txtDeckDefeat1) TextView txtDeckDefeat1;
    @Bind(R.id.txtTotalDeckDefeat1) TextView txtTotalDeckDefeat1;
    @Bind(R.id.txtDeckDefeat2) TextView txtDeckDefeat2;
    @Bind(R.id.txtTotalDeckDefeat2) TextView txtTotalDeckDefeat2;
    @Bind(R.id.txtDeckDefeat3) TextView txtDeckDefeat3;
    @Bind(R.id.txtTotalDeckDefeat3) TextView txtTotalDeckDefeat3;
    @Bind(R.id.defeats) CardView cardDefeats;

    @Bind(R.id.empty_home) TextView txtEmptyHome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return onCreateView(inflater, container, R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity.setTitle(R.string.home);
        ButterKnife.bind(this, view);
        ((MainActivity) activity).toggleIconToolbar(false);

        RealmResults<Match> matches = Realm.getDefaultInstance().where(Match.class).findAll();
        setupCardMoreUsedDecks(matches);
        setupCardGratesteDefeats(matches);

        if(matches.isEmpty()){
            txtEmptyHome.setVisibility(View.VISIBLE);
        }

        setup(chart);
        chart.setCenterText(generateCenterSpannableText());
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    private void setupCardGratesteDefeats(RealmResults<Match> matches){
        List<ItemCount> sortedList = getOpponentDecks(matches.where().equalTo("winner", false).findAll());

        if (!sortedList.isEmpty()) {
            ItemCount item = sortedList.get(0);
            txtDeckDefeat1.setText(item.getNome());
            txtTotalDeckDefeat1.setText(item.getQuantidade().toString());
            sortedList.remove(item);

            if (!sortedList.isEmpty()) {
                item = sortedList.get(0);
                txtDeckDefeat2.setText(item.getNome());
                txtTotalDeckDefeat2.setText(item.getQuantidade().toString());
                sortedList.remove(item);

                if (!sortedList.isEmpty()) {
                    item = sortedList.get(0);
                    txtDeckDefeat3.setText(item.getNome());
                    txtTotalDeckDefeat3.setText(item.getQuantidade().toString());
                } else {
                    txtDeckDefeat3.setVisibility(View.GONE);
                    txtTotalDeckDefeat3.setVisibility(View.GONE);
                }
            } else {
                txtDeckDefeat2.setVisibility(View.GONE);
                txtTotalDeckDefeat2.setVisibility(View.GONE);
                txtDeckDefeat3.setVisibility(View.GONE);
                txtTotalDeckDefeat3.setVisibility(View.GONE);
            }
        } else {
            cardDefeats.setVisibility(View.GONE);
        }
    }

    private void setupCardMoreUsedDecks(RealmResults<Match> matches) {
        List<ItemCount> sortedList = getDecks(matches.where().equalTo("winner", true).findAll());

        if (!sortedList.isEmpty()) {
            ItemCount item = sortedList.get(0);
            txtDeck1.setText(item.getNome());
            txtTotalDeck1.setText(item.getQuantidade().toString());
            sortedList.remove(item);

            if (!sortedList.isEmpty()) {
                item = sortedList.get(0);
                txtDeck2.setText(item.getNome());
                txtTotalDeck2.setText(item.getQuantidade().toString());
                sortedList.remove(item);

                if (!sortedList.isEmpty()) {
                    item = sortedList.get(0);
                    txtDeck3.setText(item.getNome());
                    txtTotalDeck3.setText(item.getQuantidade().toString());
                } else {
                    txtDeck3.setVisibility(View.GONE);
                    txtTotalDeck3.setVisibility(View.GONE);
                }
            } else {
                txtDeck2.setVisibility(View.GONE);
                txtTotalDeck2.setVisibility(View.GONE);
                txtDeck3.setVisibility(View.GONE);
                txtTotalDeck3.setVisibility(View.GONE);
            }
        } else {
            cardMoreUsedDecks.setVisibility(View.GONE);
        }
    }

    public List<ItemCount> getDecks(List<Match> matches) {
        List<Deck> decks = new ArrayList<>();
        for(Match match : matches){
            decks.add(match.getDeck());
        }

        HashMap<String, Integer> map = new HashMap<>();
        HashSet<Deck> set = new HashSet<>(decks);

        for (Deck deck : set) {
            map.put(deck.getNome(), Collections.frequency(decks, deck));
        }

        return MapUtils.sortByValue(map);
    }

    public List<ItemCount> getOpponentDecks(List<Match> matches) {
        List<Deck> decks = new ArrayList<>();
        for(Match match : matches){
            decks.add(match.getPlayerDeck());
        }

        HashMap<String, Integer> map = new HashMap<>();
        HashSet<Deck> set = new HashSet<>(decks);

        for (Deck deck : set) {
            map.put(deck.getNome(), Collections.frequency(decks, deck));
        }

        return MapUtils.sortByValue(map);
    }

    private void setData() {
        RealmResults<Match> result = RealmUtils.getInstance().getAll(Match.class);
        if(result.isEmpty()){
            cardWins.setVisibility(View.GONE);
        } else {
            txtGraphName.setText(R.string.wins_losses);
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
