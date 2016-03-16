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
import br.com.extractor.ygops.model.Player;
import br.com.extractor.ygops.util.ColorGenerator;
import br.com.extractor.ygops.util.ImageUtils;

/**
 * Created by Muryllo Tiraza on 02/02/2016.
 */
public class PlayersAdapter extends BaseAdapter {

    private List<Player> players;
    private Context context;
    private ColorGenerator colorGenerator;

    public PlayersAdapter(List<Player> players, Context context) {
        this.players = players;
        this.context = context;
        this.colorGenerator = new ColorGenerator();
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.adapter_list_item, null);
        }

        Player player = getItem(position);

        TextView txtDeck = (TextView) view.findViewById(R.id.txtName);
        txtDeck.setText(player.getNome());

        ImageView img = (ImageView) view.findViewById(R.id.image_view);
        img.setImageDrawable(ImageUtils.getInstance().getDrawable(player.getNome().substring(0, 1).toUpperCase(), colorGenerator.getColor(player.getColor())));

        return view;
    }
}
