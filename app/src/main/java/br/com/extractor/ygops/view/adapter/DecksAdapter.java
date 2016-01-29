package br.com.extractor.ygops.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Deck;

/**
 * Created by Muryllo Tiraza on 29/01/2016.
 */
public class DecksAdapter extends BaseAdapter {

    private List<Deck> decks;
    private Context context;

    public DecksAdapter(List<Deck> decks, Context context) {
        this.decks = decks;
        this.context = context;
    }

    @Override
    public int getCount() {
        return decks.size();
    }

    @Override
    public Deck getItem(int position) {
        return decks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.adapter_decks_list_item, null);
        }

        Deck deck = getItem(position);

        TextView txtDeck = (TextView) view.findViewById(R.id.txtDeck);
        txtDeck.setText(deck.getNome());

        return view;
    }
}
