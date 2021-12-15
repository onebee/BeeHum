package com.one.ui.hiitem

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.RuntimeException
import java.lang.reflect.ParameterizedType

/**
 * @author  diaokaibin@gmail.com on 2021/12/15.
 */
class HiAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mInflater: LayoutInflater? = null
    private var dataSets = ArrayList<HiDataItem<*, RecyclerView.ViewHolder>>()
    private var typeArrays = SparseArray<HiDataItem<*, RecyclerView.ViewHolder>>()

    private var mContext: Context? = null

    init {
        this.mContext = context
        this.mInflater = LayoutInflater.from(context)
    }

    fun addItem(index: Int, item: HiDataItem<*, RecyclerView.ViewHolder>, notify: Boolean) {
        if (index > 0) {
            dataSets.add(index, item)
        } else {
            dataSets.add(item)
        }


        val notifyPos = if (index > 0) index else dataSets.size - 1
        if (notify) {
            notifyItemInserted(notifyPos)
        }
    }

    fun addItems(items: List<HiDataItem<*, RecyclerView.ViewHolder>>, notify: Boolean) {

        val start = dataSets.size
        for (item in items) {
            dataSets.add(item)
        }
        if (notify) {
            notifyItemRangeInserted(start, items.size)
        }
    }

    fun removeItem(index: Int): HiDataItem<*, RecyclerView.ViewHolder>? {
        return if (index > 0 && index < dataSets.size) {
            val removeAt = dataSets.removeAt(index)
            notifyItemRemoved(index)
            removeAt
        } else {
            null
        }
    }

    fun removeItem(item: HiDataItem<*, *>) {
        if (item != null) {
            val index: Int = dataSets.indexOf(item)
            removeItem(index)
        }

    }

    override fun getItemViewType(position: Int): Int {
        val dataItem = dataSets[position]
        val type = dataItem.javaClass.hashCode()
        /***
         * 如果还没有包含这种类型的 item , 则添加进来
         */
        if (typeArrays.indexOfKey(type) < 0) {
            typeArrays.put(type, dataItem)
        }
        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val dataItem = typeArrays.get(viewType)
        var view: View? = dataItem.getItemView(parent)
        if (view == null) {
            val layoutRes = dataItem.getItemLayoutRes()
            if (layoutRes < 0) {
                RuntimeException("dataItem : " + dataItem.javaClass.name + " must override getItemView or getItemLayoutRes")
            }
            view = mInflater!!.inflate(layoutRes, parent, false)
        }
        return createViewHolderInternal(dataItem.javaClass, view)


    }

    private fun createViewHolderInternal(
        javaClass: Class<HiDataItem<*, RecyclerView.ViewHolder>>,
        view: View?
    ): RecyclerView.ViewHolder {

        val superClass = javaClass.genericSuperclass
        if (superClass is ParameterizedType) {
            val actualTypeArguments = superClass.actualTypeArguments
            for (argument in actualTypeArguments) {
                if (argument is Class<*> && RecyclerView.ViewHolder::class.java.isAssignableFrom(
                        argument
                    )
                ) {
                    // 通过反射构造出viewHolder 的实例
                    return argument.getConstructor(View::class.java)
                        .newInstance(view) as RecyclerView.ViewHolder
                }
            }
        }

        // 如果上面反射失败了 返回一个默认的 ViewHolder
        return object : RecyclerView.ViewHolder(view!!) {

        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val hiDataItem = dataSets[position]
        hiDataItem.onBindData(holder, position)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager = recyclerView.layoutManager

        if (layoutManager is GridLayoutManager) {
            val spanCount = layoutManager.spanCount
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (position < dataSets.size) {
                        val hiDataItem = dataSets[position]
                        val spanSize = hiDataItem.getSpanSize()
                        return if (spanSize <= 0) spanCount else spanSize
                    }

                    return spanCount
                }


            }

        }
    }

    override fun getItemCount(): Int {
        return dataSets.size
    }

    fun refreshItem(hiDataItem: HiDataItem<*, *>) {
        val indexOf = dataSets.indexOf(hiDataItem)
        notifyItemChanged(indexOf)

    }


}