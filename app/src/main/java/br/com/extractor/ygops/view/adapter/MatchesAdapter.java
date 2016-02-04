package br.com.extractor.ygops.view.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import br.com.extractor.ygops.model.Match;

/**
 * Created by Muryllo Tiraza on 04/02/2016.
 */
public class MatchesAdapter extends BaseAdapter {

    private List<Match> matches;

    @Override
    public int getCount() {
        return matches.size();
    }

    @Override
    public Match getItem(int position) {
        return matches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        return null;
    }
}
