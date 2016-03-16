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
import br.com.extractor.ygops.model.Player;
import br.com.extractor.ygops.util.ColorGenerator;
import br.com.extractor.ygops.util.ImageUtils;
import br.com.extractor.ygops.view.interfaces.DeleteAdapter;

/**
 * Created by Muryllo Tiraza on 05/02/2016.
 */
public class PlayersDeleteAdapter extends BaseAdapter {

    private List<PlayerSelector> playerSelectorList;
    private Context context;
    private DeleteAdapter deleteAdapter;
    private ColorGenerator colorGenerator;

    public PlayersDeleteAdapter(List<Player> players, Context context, Integer position, DeleteAdapter deleteAdapter) {
        this.context = context;
        this.deleteAdapter = deleteAdapter;
        this.colorGenerator = new ColorGenerator();

        playerSelectorList = new ArrayList<>();
        for (Player player : players) {
            playerSelectorList.add(new PlayerSelector(player));
        }

        PlayerSelector deckSelector = playerSelectorList.get(position);
        deckSelector.setIsSelect(true);
    }

    @Override
    public int getCount() {
        return playerSelectorList.size();
    }

    @Override
    public PlayerSelector getItem(int position) {
        return playerSelectorList.get(position);
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
                PlayerSelector deckSelector = getItem(position);
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

    private void updateCheckedState(ViewHolder holder, PlayerSelector playerSelector) {
        Player player = playerSelector.getPlayer();
        holder.txtNome.setText(player.getNome());

        if (playerSelector.getIsSelect()) {
            holder.imageView.setImageDrawable(
                    ImageUtils.getInstance().getDrawable("", context.getResources().getColor(R.color.accent)));
            holder.checkIcon.setVisibility(View.VISIBLE);
            holder.view.setBackgroundColor(context.getResources().getColor(R.color.selected));
        } else {
            holder.imageView.setImageDrawable(
                    ImageUtils.getInstance().getDrawable(player.getNome().substring(0, 1).toUpperCase(), colorGenerator.getColor(player.getColor())));
            holder.checkIcon.setVisibility(View.GONE);
            holder.view.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public List<Player> getSelectedItens() {
        List<Player> decks = new ArrayList<>();
        for (PlayerSelector playerSelector : playerSelectorList) {
            if (playerSelector.getIsSelect()) {
                decks.add(playerSelector.getPlayer());
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

    public class PlayerSelector {

        private Boolean isSelect;
        private Player player;

        public PlayerSelector(Player player) {
            this.isSelect = false;
            this.player = player;
        }

        public Boolean getIsSelect() {
            return isSelect;
        }

        public void setIsSelect(Boolean isSelect) {
            this.isSelect = isSelect;
        }

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }
    }
}
