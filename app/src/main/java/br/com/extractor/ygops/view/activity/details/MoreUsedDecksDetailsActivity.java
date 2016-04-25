package br.com.extractor.ygops.view.activity.details;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Deck;
import br.com.extractor.ygops.model.ItemCount;
import br.com.extractor.ygops.model.Match;
import br.com.extractor.ygops.util.ColorGenerator;
import br.com.extractor.ygops.util.RealmUtils;
import br.com.extractor.ygops.view.ParentActivity;
import br.com.extractor.ygops.view.adapter.ItemCountAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by Muryllo Tiraza on 20/04/2016.
 */
public class MoreUsedDecksDetailsActivity extends ParentActivity {

    @Bind(R.id.list) ListView list;
    @Bind(R.id.chart) PieChart chart;
    @Bind(R.id.txtTitle) TextView txtTitle;
    @Bind(R.id.txtResult) TextView txtResult;

    private Integer total = 0;
    private List<ItemCount> itens;
    private ColorGenerator colorGenerator;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.activity_card_details);
        displayHomeEnabled();
        ButterKnife.bind(this);

        RealmResults<Match> matches = RealmUtils.getInstance().getAll(Match.class).where().findAll();
        itens = getDecks(matches);

        ItemCountAdapter adapter = new ItemCountAdapter(itens, total, this);
        list.setAdapter(adapter);

        txtTitle.setText(getString(R.string.total_matches));
        txtResult.setText(total.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData(itens);
    }

    private void setData(List<ItemCount> itens) {
        colorGenerator = new ColorGenerator();

        ArrayList<Entry> yVal = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        float quantity;
        for (int i = 0; i < itens.size(); i++) {
            ItemCount item = itens.get(i);
            quantity = (item.getQuantidade() * 100.0f) / total;
            yVal.add(new Entry(quantity, i));
            xVals.add(item.getNome());
            colors.add(colorGenerator.getColor(item.getColor()));

            if (i >= 5) {
                break;
            }
        }

        if (itens.size() > 5) {
            Integer outros = 0;
            for (int i = 6; i < itens.size(); i++) {
                ItemCount item = itens.get(i);
                outros += item.getQuantidade();
            }

            quantity = (outros * 100.0f) / total;
            yVal.add(new Entry(quantity, 6));
            xVals.add(getString(R.string.others));
            colors.add(ContextCompat.getColor(this, R.color.accent));
        }

        PieDataSet dataSet = new PieDataSet(yVal, "");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        chart.setDescription("");
        chart.setDrawSliceText(false);
        chart.getLegend().setEnabled(false);
        chart.setData(data);
    }

    public List<ItemCount> getDecks(List<Match> matches) {
        List<Deck> decks = new ArrayList<>();
        for (Match match : matches) {
            decks.add(match.getDeck());
        }

        ItemCount item;
        HashSet<Deck> set = new HashSet<>(decks);
        List<ItemCount> itens = new ArrayList<>();

        for (Deck deck : set) {
            item = new ItemCount();
            item.setNome(deck.getNome());
            item.setColor(deck.getColor());
            item.setQuantidade(Collections.frequency(decks, deck));
            itens.add(item);

            total += item.getQuantidade();
        }

        Collections.sort(itens);
        return itens;
    }

}
