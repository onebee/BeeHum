package com.one.ui.hiitem

import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView

/**
 * @author  diaokaibin@gmail.com on 2021/12/15.
 */
abstract class HiDataItem<DATA, VH : RecyclerView.ViewHolder>(data: DATA?) {


    private var mAdapter: HiAdapter? = null
    private var mData: DATA? = null

    init {
        this.mData = data
    }

    abstract fun onBindData(holder: VH, pos: Int)

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
        if (mAdapter != null) {
            mAdapter!!.refreshItem(this)
        }
    }

    fun removeItem() {
        if (mAdapter != null) {
            mAdapter!!.removeItem(this)
        }
    }

    /**
     * 该item 在列表上占据几列
     */
    fun getSpanSize(): Int {
        return 0
    }

    /**
     * 该item被滑进屏幕
     */
    open fun onViewAttachedToWindow(holder: VH) {

    }

    /**
     * 该item被滑出屏幕
     */
    open fun onViewDetachedFromWindow(holder: VH) {

    }

}


