package br.com.extractor.ygops.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Deck;
import br.com.extractor.ygops.util.ColorGenerator;
import br.com.extractor.ygops.util.ImageUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by Muryllo Tiraza on 29/01/2016.
 */
public class DecksAdapter extends RealmBaseAdapter {

    private RealmResults<Deck> decks;
    private Context context;
    private ColorGenerator colorGenerator;

    public DecksAdapter(Context context, RealmResults realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);

        this.decks = realmResults;
        this.context = context;
        this.colorGenerator = new ColorGenerator();
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
        ViewHolder holder;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.adapter_list_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Deck deck = getItem(position);
        holder.txtDeck.setText(deck.getNome());
        holder.imgDeck.setImageDrawable(ImageUtils.getInstance().getDrawable(deck.getNome().substring(0, 1).toUpperCase(), colorGenerator.getColor(deck.getColor())));
        return view;
    }

    static class ViewHolder{
        @Bind(R.id.txtName) TextView txtDeck;
        @Bind(R.id.image_view)ImageView imgDeck;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
