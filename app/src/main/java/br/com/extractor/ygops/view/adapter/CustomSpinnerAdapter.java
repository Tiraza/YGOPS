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
import br.com.extractor.ygops.util.ImageUtils;
import butterknife.Bind;
import butterknife.ButterKnife;

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

    private View getCustomView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.adapter_list_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ItemAdapter item = list.get(position);
        holder.txtName.setText(item.getNome());
        holder.img.setImageDrawable(ImageUtils.getInstance().getDrawable(item.getNome().substring(0, 1).toUpperCase(), new ColorGenerator().getColor(item.getColor())));
        return view;
    }

    public void setError(View v) {
        TextView selected = (TextView) v.findViewById(R.id.txtName);
        selected.setError(context.getString(R.string.field_required));
    }

    static class ViewHolder {
        @Bind(R.id.txtName) TextView txtName;
        @Bind(R.id.image_view) ImageView img;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
