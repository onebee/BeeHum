package com.one.ui.hiitem.sample

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.one.ui.R
import com.one.ui.hiitem.HiDataItem

/**
 * @author  diaokaibin@gmail.com on 2021/12/15.
 */
class TopBanner(data: ItemData) : HiDataItem<ItemData, RecyclerView.ViewHolder>(data) {
    override fun onBindData(holder: RecyclerView.ViewHolder, pos: Int) {
        val imageview: ImageView = holder.itemView.findViewById<ImageView>(R.id.item_image);
        imageview.setImageResource(R.drawable.item_banner)
    }

    override fun getItemLayoutRes(): Int {
        return R.layout.layout_list_item_banner
    }
}