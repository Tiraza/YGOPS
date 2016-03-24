package br.com.extractor.ygops.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Match;
import br.com.extractor.ygops.model.Profile;
import br.com.extractor.ygops.util.ImageUtils;
import br.com.extractor.ygops.view.interfaces.OnDeleteRealm;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Muryllo Tiraza on 10/02/2016.
 */
public class MatchesDeleteAdapter extends BaseAdapter {

    private List<MatchSelector> matchesSelector;
    private Context context;
    private Profile profile;
    private OnDeleteRealm deleteAdapter;

    public MatchesDeleteAdapter(List<Match> matches, Context context, Realm realm, int position, OnDeleteRealm deleteAdapter) {
        this.context = context;
        this.deleteAdapter = deleteAdapter;
        profile = realm.where(Profile.class).findFirst();

        matchesSelector = new ArrayList<>();
        for (Match match : matches) {
            matchesSelector.add(new MatchSelector(match));
        }

        MatchSelector matchSelector = matchesSelector.get(position);
        matchSelector.setIsSelect(true);
    }

    @Override
    public int getCount() {
        return matchesSelector.size();
    }

    @Override
    public MatchSelector getItem(int position) {
        return matchesSelector.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup root) {
        final MatchHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.adapter_match_delete_item_list, root, false);
            holder = new MatchHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MatchHolder) convertView.getTag();
        }

        updateCheckedState(holder, getItem(position));

        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MatchSelector matchSelector = getItem(position);
                matchSelector.setIsSelect(!matchSelector.getIsSelect());

                if (getSelectedItens().size() == 0) {
                    deleteAdapter.onDelete();
                } else {
                    updateCheckedState(holder, matchSelector);
                }
            }
        });

        return convertView;
    }

    private void updateCheckedState(MatchHolder holder, MatchSelector matchSelector) {
        Match match = matchSelector.getMatch();
        holder.myName.setText(profile.getNome());
        holder.myDeck.setText(match.getDeck().getNome());
        holder.opponentName.setText(match.getPlayer().getNome());
        holder.opponentDeck.setText(match.getPlayerDeck().getNome());

        if (matchSelector.getIsSelect()) {
            holder.icon.setVisibility(View.VISIBLE);
            holder.row.setBackgroundColor(ContextCompat.getColor(context, R.color.selected));
            holder.image.setImageDrawable(ImageUtils.getInstance().getDrawable("", ContextCompat.getColor(context, R.color.accent)));
        } else {
            holder.icon.setVisibility(View.GONE);
            holder.row.setBackgroundColor(Color.TRANSPARENT);
            if (match.getWinner()) {
                holder.image.setImageDrawable(
                        ImageUtils.getInstance().getDrawable(
                                context.getResources().getString(R.string.match_winner_hint), ContextCompat.getColor(context, R.color.match_winner)));
            } else {
                holder.image.setImageDrawable(
                        ImageUtils.getInstance().getDrawable(
                                context.getResources().getString(R.string.match_loser_hint), ContextCompat.getColor(context, R.color.match_loser)));
            }
        }
    }

    public List<Match> getSelectedItens() {
        List<Match> matches = new ArrayList<>();
        for (MatchSelector matchSelector : matchesSelector) {
            if (matchSelector.getIsSelect()) {
                matches.add(matchSelector.getMatch());
            }
        }
        return matches;
    }

    static class MatchHolder {
        View row;
        @Bind(R.id.check_icon)
        ImageView icon;
        @Bind(R.id.image_view)
        ImageView image;
        @Bind(R.id.txtPlayer1)
        TextView myName;
        @Bind(R.id.txtDeck1)
        TextView myDeck;
        @Bind(R.id.txtPlayer2)
        TextView opponentName;
        @Bind(R.id.txtDeck2)
        TextView opponentDeck;

        public MatchHolder(View view) {
            row = view;
            ButterKnife.bind(this, view);
        }
    }

    public class MatchSelector {

        private Boolean isSelect;
        private Match match;

        public MatchSelector(Match match) {
            this.isSelect = false;
            this.match = match;
        }

        public Boolean getIsSelect() {
            return isSelect;
        }

        public void setIsSelect(Boolean isSelect) {
            this.isSelect = isSelect;
        }

        public Match getMatch() {
            return match;
        }

        public void setMatch(Match match) {
            this.match = match;
        }
    }
}
