package br.com.extractor.ygops.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.List;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Match;
import br.com.extractor.ygops.model.Profile;
import br.com.extractor.ygops.util.ImageUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Muryllo Tiraza on 04/02/2016.
 */
public class MatchesAdapter extends BaseAdapter {

    private List<Match> matches;
    private Context context;
    private Profile profile;

    public MatchesAdapter(List<Match> matches, Context context, Realm realm) {
        this.matches = matches;
        this.context = context;
        this.profile = realm.where(Profile.class).findFirst();
    }

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
    public View getView(int position, View convertView, ViewGroup parent) {
        MatchHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.adapter_match_list_item, null);
            holder = new MatchHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MatchHolder) convertView.getTag();
        }

        Match match = matches.get(position);
        holder.myName.setText(profile.getNome());
        holder.myDeck.setText(match.getDeck().getNome());
        holder.opponentName.setText(match.getPlayer().getNome());
        holder.opponentDeck.setText(match.getPlayerDeck().getNome());

        if (match.getWinner()) {
            holder.image.setImageDrawable(
                    ImageUtils.getInstance().getDrawable( context.getResources().getString(R.string.match_winner_hint),context.getResources().getColor(R.color.match_winner)));
        } else {
            holder.image.setImageDrawable(
                    ImageUtils.getInstance().getDrawable(context.getResources().getString(R.string.match_loser_hint),context.getResources().getColor(R.color.match_loser)));
        }

        return convertView;
    }

    static class MatchHolder {
        View row;
        @Bind(R.id.image_view) ImageView image;
        @Bind(R.id.txtPlayer1) TextView myName;
        @Bind(R.id.txtDeck1) TextView myDeck;
        @Bind(R.id.txtPlayer2) TextView opponentName;
        @Bind(R.id.txtDeck2) TextView opponentDeck;

        public MatchHolder(View view) {
            row = view;
            ButterKnife.bind(this, view);
        }
    }

}
