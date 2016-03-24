package br.com.extractor.ygops.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Deck;
import br.com.extractor.ygops.util.ColorGenerator;
import br.com.extractor.ygops.util.ImageUtils;
import br.com.extractor.ygops.view.interfaces.OnDeleteRealm;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by Muryllo Tiraza on 05/02/2016.
 */
public class DecksDeleteAdapter extends RealmBaseAdapter {

    private Context context;
    private RealmResults<Deck> decks;
    private List<String> positionsSelected;
    private OnDeleteRealm deleteRealm;
    private ColorGenerator colorGenerator;

    public DecksDeleteAdapter(RealmResults<Deck> decks, Context context, OnDeleteRealm deleteAdapter, String uuid) {
        super(context, decks, true);

        this.decks = decks;
        this.context = context;
        this.deleteRealm = deleteAdapter;
        this.colorGenerator = new ColorGenerator();

        positionsSelected = new ArrayList<>();
        positionsSelected.add(uuid);
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
    public View getView(final int position, View view, ViewGroup root) {
        final ViewHolder holder;
        final Deck deck = getItem(position);

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.adapter_delete_list_item, root, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        updateCheckedState(holder, deck);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uuid = deck.getUuid();

                if(positionsSelected.contains(uuid)){
                    positionsSelected.remove(uuid);
                } else {
                    positionsSelected.add(uuid);
                }

                if (positionsSelected.size() == 0) {
                    deleteRealm.onDelete();
                } else {
                    updateCheckedState(holder, deck);
                }
            }
        });

        return view;
    }

    private void updateCheckedState(ViewHolder holder, Deck deck) {
        holder.txtNome.setText(deck.getNome());

        if (positionsSelected.contains(deck.getUuid())) {
            holder.imageView.setImageDrawable(ImageUtils.getInstance().getDrawable("", ContextCompat.getColor(context, R.color.accent)));
            holder.checkIcon.setVisibility(View.VISIBLE);
            holder.view.setBackgroundColor(ContextCompat.getColor(context, R.color.selected));
        } else {
            holder.imageView.setImageDrawable(ImageUtils.getInstance().getDrawable(deck.getNome().substring(0, 1).toUpperCase(), colorGenerator.getColor(deck.getColor())));
            holder.checkIcon.setVisibility(View.GONE);
            holder.view.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public List<String> getSelectedItens() {
        return positionsSelected;
    }

    static class ViewHolder {
        View view;
        @Bind(R.id.txtDeck) TextView txtNome;
        @Bind(R.id.image_view) ImageView imageView;
        @Bind(R.id.check_icon) ImageView checkIcon;

        private ViewHolder(View view) {
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}
