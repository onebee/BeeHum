package com.one.ui.hiitem

import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView

/**
 * @author  diaokaibin@gmail.com on 2021/12/15.
 */
abstract class HiDataItem<DATA, VH : RecyclerView.ViewHolder>(data: DATA) {


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

    fun refreshItem(){

    }

    fun removeItem(){}


}


