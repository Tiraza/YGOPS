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
import br.com.extractor.ygops.model.Player;
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
public class PlayersDeleteAdapter extends RealmBaseAdapter {

    private Context context;
    private OnDeleteRealm deleteAdapter;
    private ColorGenerator colorGenerator;
    private List<String> positionsSelected;
    private RealmResults<Player> players;

    public PlayersDeleteAdapter(RealmResults<Player> players, Context context, OnDeleteRealm deleteAdapter, String uuid) {
        super(context, players, true);

        this.players = players;
        this.context = context;
        this.deleteAdapter = deleteAdapter;
        this.colorGenerator = new ColorGenerator();

        positionsSelected = new ArrayList<>();
        positionsSelected.add(uuid);
    }

    @Override
    public int getCount() {
        return players.size();
    }

    @Override
    public Player getItem(int position) {
        return players.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup root) {
        final ViewHolder holder;
        final Player player = getItem(position);

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.adapter_delete_list_item, root, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        updateCheckedState(holder, player);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uuid = player.getUuid();

                if(positionsSelected.contains(uuid)){
                    positionsSelected.remove(uuid);
                } else {
                    positionsSelected.add(uuid);
                }

                if (positionsSelected.size() == 0) {
                    deleteAdapter.onDelete();
                } else {
                    updateCheckedState(holder, player);
                }
            }
        });

        return view;
    }

    private void updateCheckedState(ViewHolder holder, Player player) {
        holder.txtNome.setText(player.getNome());

        if (positionsSelected.contains(player.getUuid())) {
            holder.imageView.setImageDrawable(ImageUtils.getInstance().getDrawable("", ContextCompat.getColor(context, R.color.accent)));
            holder.checkIcon.setVisibility(View.VISIBLE);
            holder.view.setBackgroundColor(ContextCompat.getColor(context, R.color.selected));
        } else {
            holder.imageView.setImageDrawable(
                    ImageUtils.getInstance().getDrawable(player.getNome().substring(0, 1).toUpperCase(), colorGenerator.getColor(player.getColor())));
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
        @Bind(R.id.check_icon) ImageView checkIcon;
        @Bind(R.id.image_view) ImageView imageView;

        private ViewHolder(View view) {
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}
