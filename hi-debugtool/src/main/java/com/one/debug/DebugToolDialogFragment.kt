package com.one.debug

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.one.library.util.HiDisplayUtil
import kotlinx.android.synthetic.main.hi_debug_tool.*
import java.lang.reflect.Method

/**
 * @author  diaokaibin@gmail.com on 2021/8/1.
 */
class DebugToolDialogFragment : DialogFragment() {

    private val debugTools = arrayOf(DebugTools::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogFragmentStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val parent = dialog?.window?.findViewById<ViewGroup>(android.R.id.content) ?: container
        val view = inflater.inflate(R.layout.hi_debug_tool, parent, false)
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL)
        dialog?.window?.setLayout(
            (HiDisplayUtil.getDisplayHeightInPx(view.context) * 0.5f).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )


        dialog?.window?.setBackgroundDrawableResource(R.drawable.shape_hi_debug_tool)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dividerItemDecoration =
            DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(
                view.context,
                R.drawable.shape_hi_debug_divider
            )!!
        )

        val functions = mutableListOf<DebugFunction>()
        val size = debugTools.size
        for (index in 0 until size) {
            val clz = debugTools[index]
            val target = clz.getConstructor().newInstance()
            for (method in target.javaClass.declaredMethods) {
                var title = ""
                var desc = ""
                var enable = false
                val annotation = method.getAnnotation(HiDebug::class.java)
                if (annotation != null) {
                    title = annotation.name
                    desc = annotation.desc
                    enable = true
                } else {
                    // 有直接返回值的方法
                    method.isAccessible = true
                    title = method.invoke(target) as String
                }

                val func = DebugFunction(title, desc, method, enable, target)
                functions.add(func)
            }
        }


        recycler_view.addItemDecoration(dividerItemDecoration)
        recycler_view.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        recycler_view.adapter  = DebugToolAdapter(functions)

    }

    inner class DebugToolAdapter(private val list: List<DebugFunction>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val itemView = layoutInflater.inflate(R.layout.hi_debug_tool_item, parent, false)
            return object : RecyclerView.ViewHolder(itemView) {}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            val debugFunction = list[position]
            val itemTitle = holder.itemView.findViewById<TextView>(R.id.item_title)
            val itemDesc = holder.itemView.findViewById<TextView>(R.id.item_desc)

            itemTitle.text = debugFunction.name
            if (TextUtils.isEmpty(debugFunction.desc)) {
                itemDesc.visibility = View.GONE
            } else {
                itemDesc.visibility = View.VISIBLE
                itemDesc.text = debugFunction.desc
            }

            if (debugFunction.enable) {
                holder.itemView.setOnClickListener {
                    debugFunction.invoke()
                }
            }

        }

        override fun getItemCount(): Int {
            return list.size
        }

    }

    data class DebugFunction(
        val name: String,
        val desc: String,
        val method: Method,
        val enable: Boolean,
        val target: Any
    ) {
        fun invoke() {
            method.invoke(target)
        }

    }
}