package br.com.extractor.ygops.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.util.ColorGenerator;
import br.com.extractor.ygops.util.ImageUtils;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Muryllo Tiraza on 04/02/2016.
 */
public class ColorAdapter extends ArrayAdapter implements SpinnerAdapter {

    private Context context;
    private static ColorGenerator colorGenerator = new ColorGenerator();

    public ColorAdapter(Context context) {
        super(context, R.layout.adapter_color_list_item, colorGenerator.getList());
        this.context = context;
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
        return colorGenerator.getList().size();
    }

    private View getCustomView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.adapter_color_list_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.imgColor.setImageDrawable(ImageUtils.getInstance().getDrawable("", colorGenerator.getColor(position)));
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.imgColor)
        ImageView imgColor;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
