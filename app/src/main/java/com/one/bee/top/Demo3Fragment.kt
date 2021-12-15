package com.one.bee.top

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.one.bee.R
import com.one.common.ui.component.HiBaseFragment
import com.one.ui.hiitem.HiAdapter
import com.one.ui.hiitem.HiDataItem
import com.one.ui.hiitem.sample.GirdDataItem
import com.one.ui.hiitem.sample.ItemData
import com.one.ui.hiitem.sample.TopBanner
import com.one.ui.hiitem.sample.TopTabDataItem
import kotlinx.android.synthetic.main.demo1_fragment.*
import kotlinx.android.synthetic.main.hiitem_fragment.*

/**
 * @author  diaokaibin@gmail.com on 2021/11/17.
 */
class Demo3Fragment : HiBaseFragment() {
    

    override fun getLayoutId(): Int {
        return R.layout.hiitem_fragment
    }


    override fun onResume() {
        super.onResume()

        var hiAdapter = HiAdapter(context!!)
        rv.layoutManager = GridLayoutManager(context,2)
        rv.adapter = hiAdapter

        var dataSets: ArrayList<HiDataItem<*, out RecyclerView.ViewHolder>> = ArrayList()
        dataSets.add(TopTabDataItem(ItemData()))
        dataSets.add(TopBanner(ItemData()))
        dataSets.add(GirdDataItem(ItemData()))

        hiAdapter.addItems(dataSets,false)

    }
}