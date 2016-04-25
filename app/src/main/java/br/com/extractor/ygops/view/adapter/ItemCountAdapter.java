package br.com.extractor.ygops.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.ItemCount;
import br.com.extractor.ygops.util.ColorGenerator;
import br.com.extractor.ygops.util.ImageUtils;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Muryllo Tiraza on 20/04/2016.
 */
public class ItemCountAdapter extends BaseAdapter {

    private Context context;
    private List<ItemCount> itens;
    private Integer total;
    private ColorGenerator colorGenerator;

    public ItemCountAdapter(List<ItemCount> itens, Integer total, Context context) {
        this.itens = itens;
        this.total = total;
        this.context = context;
        this.colorGenerator = new ColorGenerator();
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public ItemCount getItem(int position) {
        return itens.get(position);
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
            view = layoutInflater.inflate(R.layout.adapter_item_count_list_item, root, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ItemCount item = getItem(position);
        holder.txtName.setText(item.getNome());
        holder.txtDetails.setText(getStringDetatils(item));
        holder.img.setImageDrawable(ImageUtils.getInstance().getDrawable(item.getNome().substring(0, 1).toUpperCase(), colorGenerator.getColor(item.getColor())));

        return view;
    }

    @NonNull
    private String getStringDetatils(ItemCount item) {
        float percetage = (item.getQuantidade() * 100.0f) / total;
        StringBuilder string = new StringBuilder();
        string.append(context.getString(R.string.quantity)).append(": ").append(item.getQuantidade());
        string.append(" / ");
        string.append(context.getString(R.string.percentage)).append(": ").append(String.format(context.getResources().getConfiguration().locale, "%.1f", percetage)).append("%");
        return string.toString();
    }

    static class ViewHolder {
        @Bind(R.id.txtName) TextView txtName;
        @Bind(R.id.txtDetails) TextView txtDetails;
        @Bind(R.id.image_view) ImageView img;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
