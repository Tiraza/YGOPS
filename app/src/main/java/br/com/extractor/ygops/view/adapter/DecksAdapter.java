package br.com.extractor.ygops.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Deck;
import br.com.extractor.ygops.util.ColorGenerator;
import br.com.extractor.ygops.util.ImageUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by Muryllo Tiraza on 29/01/2016.
 */
public class DecksAdapter extends RealmBaseAdapter {

    private Context context;
    private RealmResults<Deck> originalDecks;
    private ColorGenerator colorGenerator;

    public DecksAdapter(Context context, RealmResults<Deck> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);

        this.originalDecks = realmResults;
        this.context = context;
        this.colorGenerator = new ColorGenerator();
    }

    @Override
    public int getCount() {
        return originalDecks.size();
    }

    @Override
    public Deck getItem(int position) {
        return originalDecks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup root) {
        ViewHolder holder;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.adapter_list_item, root, false);
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

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                return new FilterResults();
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                originalDecks = Realm.getDefaultInstance().where(Deck.class).contains("nome", constraint.toString(), Case.INSENSITIVE).findAll();
                DecksAdapter.this.notifyDataSetChanged();
            }
        };
    }

    static class ViewHolder {
        @Bind(R.id.txtName) TextView txtDeck;
        @Bind(R.id.image_view) ImageView imgDeck;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
