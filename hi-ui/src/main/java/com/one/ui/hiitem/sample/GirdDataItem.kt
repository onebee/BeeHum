package com.one.ui.hiitem.sample

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.one.ui.R
import com.one.ui.hiitem.HiDataItem

/**
 * @author  diaokaibin@gmail.com on 2021/12/15.
 */
class GirdDataItem(data: ItemData):HiDataItem<ItemData, GirdDataItem.MyHolder>(data) {

     class MyHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var imageView:ImageView? = null

        init {
            imageView = itemView.findViewById(R.id.item_image)
        }
    }

    override fun onBindData(holder: MyHolder, pos: Int) {
       holder.imageView!!.setImageResource(R.drawable.item_grid)
    }

    override fun getItemLayoutRes(): Int {
        return R.layout.layout_list_item_grid
    }
}