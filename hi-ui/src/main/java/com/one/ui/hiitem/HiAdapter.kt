package com.one.ui.hiitem

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import java.lang.RuntimeException
import java.lang.ref.WeakReference
import java.lang.reflect.ParameterizedType

/**
 * @author  diaokaibin@gmail.com on 2021/12/15.
 */
class HiAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mInflater: LayoutInflater? = null
    private var dataSets = ArrayList<HiDataItem<*, out RecyclerView.ViewHolder>>()
    private var typeArrays = SparseArray<HiDataItem<*, out RecyclerView.ViewHolder>>()
    private val recyclerViewRef: WeakReference<RecyclerView>? = null

    private var mContext: Context? = null

    init {
        this.mContext = context
        this.mInflater = LayoutInflater.from(context)
    }

    fun addItemAt(index: Int, item: HiDataItem<*, out RecyclerView.ViewHolder>, notify: Boolean) {
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

    fun addItems(items: List<HiDataItem<*, out RecyclerView.ViewHolder>>, notify: Boolean) {

        val start = dataSets.size
        for (item in items) {
            dataSets.add(item)
        }
        if (notify) {
            notifyItemRangeInserted(start, items.size)
        }
    }

    fun removeItemAt(index: Int): HiDataItem<*, out RecyclerView.ViewHolder>? {
        return if (index > 0 && index < dataSets.size) {
            val removeAt = dataSets.removeAt(index)
            notifyItemRemoved(index)
           return removeAt
        } else {
            null
        }
    }

    fun removeItem(item: HiDataItem<*, out RecyclerView.ViewHolder>) {
        val index: Int = dataSets.indexOf(item)
        removeItemAt(index)

    }



    override fun getItemViewType(position: Int): Int {
        val dataItem = dataSets[position]
        val type = dataItem.javaClass.hashCode()
        /***
         * ???????????????????????????????????? item , ???????????????
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
        return createViewHolderInternal(dataItem.javaClass, view!!)


    }

    private fun createViewHolderInternal(
        javaClass: Class<HiDataItem<*, out RecyclerView.ViewHolder>>,
        view: View
    ): RecyclerView.ViewHolder {

        //?????????Item???????????????,??????HiDataItem.class???  class ??????type??????????????????
        //type????????????????????? class????????????,ParameterizedType???????????? ???TypeVariable????????????
        //?????????????????????????????????????????????
        val superClass = javaClass.genericSuperclass
        if (superClass is ParameterizedType) {
            //???????????????????????????????????????
            val actualTypeArguments = superClass.actualTypeArguments
            //?????????????????? ???????????????????????? RecyclerView.ViewHolder ????????????
            for (argument in actualTypeArguments) {
                if (argument is Class<*> && RecyclerView.ViewHolder::class.java.isAssignableFrom(
                        argument
                    )
                ) {

                    try {
                        //???????????????????????? ?????????????????????????????????????????????
                        //????????????  try-catch ??????????????????????????????HiDataItem??????????????? RecyclerView.ViewHolder?????????????????????????????????
                        // ?????????????????????viewHolder ?????????
                        return argument.getConstructor(View::class.java)
                            .newInstance(view) as RecyclerView.ViewHolder
                    }catch (e : Throwable){
                        e.printStackTrace()
                    }

                }
            }
        }

        // ??????????????????????????? ????????????????????? ViewHolder
        return object : RecyclerView.ViewHolder(view) {}

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val hiDataItem = getItem(position)
        hiDataItem?.onBindData(holder, position)
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

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        if (recyclerViewRef != null) {
            recyclerViewRef.clear()
        }
    }
    fun getAttachRecyclerView(): RecyclerView? {
        return if (recyclerViewRef != null) recyclerViewRef.get() else null
    }

    fun getItem(position: Int): HiDataItem<*, RecyclerView.ViewHolder>? {
        if (position < 0 || position >= dataSets.size)
            return null
        return dataSets[position] as HiDataItem<*, RecyclerView.ViewHolder>
    }

    override fun getItemCount(): Int {
        return dataSets.size
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        val recyclerView = getAttachRecyclerView()
        if (recyclerView != null) {
            //????????????item????????????
            val position = recyclerView.getChildAdapterPosition(holder.itemView)
            val dataItem = getItem(position)
            if (dataItem == null) return
            val lp = holder.itemView.layoutParams
            if (lp != null && lp is StaggeredGridLayoutManager.LayoutParams) {
                val manager = recyclerView.layoutManager as StaggeredGridLayoutManager?
                val spanSize = dataItem.getSpanSize()
                if (spanSize == manager!!.spanCount) {
                    lp.isFullSpan = true
                }
            }

            dataItem.onViewAttachedToWindow(holder)
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        val dataItem = getItem(holder.adapterPosition)
        if (dataItem == null) return
        dataItem.onViewDetachedFromWindow(holder)
    }

    fun refreshItem(hiDataItem: HiDataItem<*, out RecyclerView.ViewHolder>) {
        val indexOf = dataSets.indexOf(hiDataItem)
        notifyItemChanged(indexOf)

    }


}