package br.com.extractor.ygops.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;

import com.amulyakhare.textdrawable.TextDrawable;
import br.com.extractor.ygops.R;
import br.com.extractor.ygops.util.ColorGenerator;

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

    private View getCustomView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.adapter_color_list_item, parent, false);
        }

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .bold()
                .endConfig()
                .buildRound("", colorGenerator.getColor(position));

        ImageView img = (ImageView) convertView.findViewById(R.id.imgColor);
        img.setImageDrawable(drawable);

        return convertView;
    }
}
