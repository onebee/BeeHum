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

    private var headers = SparseArray<View>()
    private var footers = SparseArray<View>()

    private var BASE_ITEM_TYPE_HEADER = 1000000
    private var BASE_ITEM_TYPE_FOOTER = 1000000
    fun addHeaderView(view: View) {
        if (headers.indexOfValue(view) < 0) {
            //没有添加过
            headers.put(BASE_ITEM_TYPE_HEADER++, view)
            notifyItemInserted(headers.size() - 1)
        }

    }

    fun removeHeaderView(view: View) {
        val indexOfValue = headers.indexOfValue(view)
        if (indexOfValue < 0) return
        headers.removeAt(indexOfValue)
        notifyItemRemoved(indexOfValue)
    }

    fun addFooterView(view: View) {
        if (footers.indexOfValue(view) < 0) {
            footers.put(BASE_ITEM_TYPE_FOOTER++, view)
            notifyItemInserted(itemCount)
        }
    }

    fun removeFooterView(view: View) {
        val indexOfValue = footers.indexOfValue(view)
        if (indexOfValue < 0) return
        footers.removeAt(indexOfValue)
        notifyItemRemoved(indexOfValue + getHeaderSize() + getOriginalItemSize())
    }

    fun getFootSize(): Int {
        return footers.size()
    }

    fun getHeaderSize(): Int {
        return headers.size()
    }

    fun getOriginalItemSize(): Int {
        return dataSets.size
    }


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


    /**
     * 以每种item 类型的class.hascode 为该item 的viewType
     * 这里把type 存储起来, 是为了onCreateViewHolder 方法能够为不同类型的item
     * 创建不同的viewHolher
     */
    override fun getItemViewType(position: Int): Int {
        if (isHeaderPosition(position)) {
            return headers.keyAt(position)
        }

        if (isFooterPosition(position)) {
            // footer 的位置需要计算一下
            val footerPosition = position - getHeaderSize() - getOriginalItemSize()
            return footers.keyAt(footerPosition)
        }


        val itemPosition = position - getHeaderSize()

        val dataItem = dataSets[itemPosition]
        val type = dataItem.javaClass.hashCode()
        /***
         * 如果还没有包含这种类型的 item , 则添加进来
         */
        if (typeArrays.indexOfKey(type) < 0) {
            typeArrays.put(type, dataItem)
        }
        return type
    }

    private fun isFooterPosition(position: Int): Boolean {
        return position >= getHeaderSize() + getOriginalItemSize()
    }

    private fun isHeaderPosition(position: Int): Boolean {
        return position < headers.size()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (headers.indexOfKey(viewType) >= 0) {
            val view = headers[viewType]
            return object : RecyclerView.ViewHolder(view) {}
        }

        if (footers.indexOfKey(viewType) >= 0) {
            val view = footers[viewType]
            return object : RecyclerView.ViewHolder(view) {}
        }


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

        //得到该Item的父类类型,即为HiDataItem.class。  class 也是type的一个子类。
        //type的子类常见的有 class，类泛型,ParameterizedType参数泛型 ，TypeVariable字段泛型
        //所以进一步判断它是不是参数泛型
        val superClass = javaClass.genericSuperclass
        if (superClass is ParameterizedType) {
            //得到它携带的泛型参数的数组
            val actualTypeArguments = superClass.actualTypeArguments
            //挨个遍历判断 是不是咱们想要的 RecyclerView.ViewHolder 类型的。
            for (argument in actualTypeArguments) {
                if (argument is Class<*> && RecyclerView.ViewHolder::class.java.isAssignableFrom(
                        argument
                    )
                ) {

                    try {
                        //如果是则使用反射 实例化类上标记的实际的泛型对象
                        //这里需要  try-catch 一把，如果咱们直接在HiDataItem子类上标记 RecyclerView.ViewHolder，抽象类是不允许反射的
                        // 通过反射构造出viewHolder 的实例
                        return argument.getConstructor(View::class.java)
                            .newInstance(view) as RecyclerView.ViewHolder
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }

                }
            }
        }

        // 如果上面反射失败了 返回一个默认的 ViewHolder
        return object : RecyclerView.ViewHolder(view) {}

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isFooterPosition(position) || isHeaderPosition(position))return

        val itemPos = position-getHeaderSize()

        val hiDataItem = getItem(itemPos)
        hiDataItem?.onBindData(holder, itemPos)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager = recyclerView.layoutManager

        // 为列表上的item 适配网格布局
        if (layoutManager is GridLayoutManager) {
            val spanCount = layoutManager.spanCount
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (isHeaderPosition(position) || isFooterPosition(position)){
                        return spanCount
                    }

                    val itemPosition = position-getHeaderSize()

                    if (itemPosition < dataSets.size) {
                        val hiDataItem = dataSets[itemPosition]
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
        return dataSets.size + getHeaderSize() + getFootSize()
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        val recyclerView = getAttachRecyclerView()
        if (recyclerView != null) {
            //瀑布流的item占比适配
            val position = recyclerView.getChildAdapterPosition(holder.itemView)
            val isHeaderFooter = isHeaderPosition(position) || isFooterPosition(position)
            val itemPosition = position - getHeaderSize()


            val dataItem = getItem(itemPosition)
            if (dataItem == null) return
            val lp = holder.itemView.layoutParams
            if (lp != null && lp is StaggeredGridLayoutManager.LayoutParams) {
                val manager = recyclerView.layoutManager as StaggeredGridLayoutManager?
                if (isHeaderFooter) {
                    lp.isFullSpan = true
                    return
                }

                val spanSize = dataItem.getSpanSize()
                if (spanSize == manager!!.spanCount) {
                    lp.isFullSpan = true
                }
            }

            dataItem.onViewAttachedToWindow(holder)
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        val position = holder.adapterPosition
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            return
        }

        val itemPosition = position - getHeaderSize()



        val dataItem = getItem(itemPosition)
        if (dataItem == null) return
        dataItem.onViewDetachedFromWindow(holder)
    }

    fun refreshItem(hiDataItem: HiDataItem<*, out RecyclerView.ViewHolder>) {
        val indexOf = dataSets.indexOf(hiDataItem)
        notifyItemChanged(indexOf)

    }


}