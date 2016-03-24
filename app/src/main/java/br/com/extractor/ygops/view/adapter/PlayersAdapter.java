package br.com.extractor.ygops.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Player;
import br.com.extractor.ygops.util.ColorGenerator;
import br.com.extractor.ygops.util.ImageUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by Muryllo Tiraza on 02/02/2016.
 */
public class PlayersAdapter extends RealmBaseAdapter {

    private Context context;
    private RealmResults<Player> players;
    private ColorGenerator colorGenerator;

    public PlayersAdapter(RealmResults<Player> players, Context context) {
        super(context, players, true);

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

        Player player = getItem(position);
        holder.txtPlayer.setText(player.getNome());
        holder.imgPlayer.setImageDrawable(ImageUtils.getInstance().getDrawable(player.getNome().substring(0, 1).toUpperCase(), colorGenerator.getColor(player.getColor())));
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.txtName) TextView txtPlayer;
        @Bind(R.id.image_view) ImageView imgPlayer;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
