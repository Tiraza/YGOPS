package br.com.extractor.ygops.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.ArrayList;
import java.util.List;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Deck;
import br.com.extractor.ygops.util.ColorGenerator;
import br.com.extractor.ygops.view.interfaces.DeleteAdapter;

/**
 * Created by Muryllo Tiraza on 05/02/2016.
 */
public class DecksDeleteAdapter extends BaseAdapter {

    private List<DeckSelector> deckSelectorList;
    private Context context;
    private DeleteAdapter deleteAdapter;

    public DecksDeleteAdapter(List<Deck> decks, Context context, Integer position, DeleteAdapter deleteAdapter) {
        this.context = context;
        this.deleteAdapter = deleteAdapter;

        deckSelectorList = new ArrayList<>();
        for (Deck deck : decks) {
            deckSelectorList.add(new DeckSelector(deck));
        }

        DeckSelector deckSelector = deckSelectorList.get(position);
        deckSelector.setIsSelect(true);
    }

    @Override
    public int getCount() {
        return deckSelectorList.size();
    }

    @Override
    public DeckSelector getItem(int position) {
        return deckSelectorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.adapter_delete_list_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        updateCheckedState(holder, getItem(position));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeckSelector deckSelector = getItem(position);
                deckSelector.setIsSelect(!deckSelector.getIsSelect());

                if (getSelectedItens().size() == 0) {
                    deleteAdapter.onDelete();
                } else {
                    updateCheckedState(holder, deckSelector);
                }
            }
        });

        return view;
    }

    private void updateCheckedState(ViewHolder holder, DeckSelector deckSelector) {
        Deck deck = deckSelector.getDeck();
        holder.txtNome.setText(deck.getNome());

        if (deckSelector.getIsSelect()) {
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .bold()
                    .endConfig()
                    .buildRound("", context.getResources().getColor(R.color.accent));

            holder.imageView.setImageDrawable(drawable);
            holder.checkIcon.setVisibility(View.VISIBLE);
            holder.view.setBackgroundColor(context.getResources().getColor(R.color.selected));
        } else {
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .bold()
                    .endConfig()
                    .buildRound(deck.getNome().substring(0, 1).toUpperCase(), new ColorGenerator().getColor(deck.getColor()));

            holder.imageView.setImageDrawable(drawable);
            holder.checkIcon.setVisibility(View.GONE);
            holder.view.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public List<Deck> getSelectedItens() {
        List<Deck> decks = new ArrayList<>();
        for (DeckSelector deckSelector : deckSelectorList) {
            if (deckSelector.getIsSelect()) {
                decks.add(deckSelector.getDeck());
            }
        }
        return decks;
    }

    private static class ViewHolder {

        private View view;
        private ImageView imageView;
        private TextView txtNome;
        private ImageView checkIcon;

        private ViewHolder(View view) {
            this.view = view;
            imageView = (ImageView) view.findViewById(R.id.image_view);
            txtNome = (TextView) view.findViewById(R.id.txtDeck);
            checkIcon = (ImageView) view.findViewById(R.id.check_icon);
        }
    }

    public class DeckSelector {

        private Boolean isSelect;
        private Deck deck;

        public DeckSelector(Deck deck) {
            this.isSelect = false;
            this.deck = deck;
        }

        public Boolean getIsSelect() {
            return isSelect;
        }

        public void setIsSelect(Boolean isSelect) {
            this.isSelect = isSelect;
        }

        public Deck getDeck() {
            return deck;
        }

        public void setDeck(Deck deck) {
            this.deck = deck;
        }
    }
}
