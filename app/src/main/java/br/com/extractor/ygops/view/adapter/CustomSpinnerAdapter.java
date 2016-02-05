package br.com.extractor.ygops.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.List;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.ItemAdapter;
import br.com.extractor.ygops.util.ColorGenerator;

/**
 * Created by Muryllo Tiraza on 05/02/2016.
 */
public class CustomSpinnerAdapter extends ArrayAdapter implements SpinnerAdapter {

    private Context context;
    private List<ItemAdapter> list;

    public CustomSpinnerAdapter(Context context, List<ItemAdapter> list) {
        super(context, R.layout.adapter_list_item, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.adapter_list_item, parent, false);
        }

        ItemAdapter item = list.get(position);

        TextView txtDeck = (TextView) convertView.findViewById(R.id.txtDeck);
        txtDeck.setText(item.getNome());

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .bold()
                .endConfig()
                .buildRound(item.getNome().substring(0, 1).toUpperCase(), new ColorGenerator().getColor(item.getColor()));

        ImageView img = (ImageView) convertView.findViewById(R.id.image_view);
        img.setImageDrawable(drawable);

        return convertView;
    }
}