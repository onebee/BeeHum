package com.one.ui.hiitem

import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView

/**
 * @author  diaokaibin@gmail.com on 2021/12/15.
 */
abstract class HiDataItem<DATA, VH : RecyclerView.ViewHolder>(data: DATA) {


    private lateinit var mAdapter: HiAdapter
    var mData: DATA? = null

    init {
        this.mData = data
    }

    abstract fun onBindData(holder: RecyclerView.ViewHolder, pos: Int)

    open fun getItemLayoutRes(): Int {
        return -1
    }

    open fun getItemView(parent: ViewGroup): View? {
        return null
    }


    fun setAdapter(adapter: HiAdapter) {
        this.mAdapter = adapter
    }

    fun refreshItem() {
        mAdapter.refreshItem(this)
    }

    fun removeItem() {
        mAdapter.removeItem(this)
    }

    /**
     * 该item 在列表上占据几列
     */
    fun getSpanSize(): Int {
        return 0
    }


}


