package com.one.ui.hiitem.sample;

import android.widget.ImageView;

import com.one.ui.R;
import com.one.ui.hiitem.HiDataItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author diaokaibin@gmail.com on 2021/12/15.
 */
public class TopTabDataItem extends HiDataItem<ItemData, RecyclerView.ViewHolder> {
    public TopTabDataItem(ItemData data) {
        super(data);
    }

    @Override
    public int getItemLayoutRes() {
        return R.layout.layout_list_item_top_tab;
    }

    @Override
    public void onBindData(@NonNull RecyclerView.ViewHolder holder, int pos) {
        ImageView img = holder.itemView.findViewById(R.id.item_image);
        img.setImageResource(R.drawable.item_top_tab);
    }
}