package com.one.bee.top

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.one.bee.BankActivity
import com.one.bee.ExcelActivity
import com.one.bee.R
import com.one.bee.mo.BannerMo
import com.one.common.ui.component.HiBaseFragment
import com.one.library.log.HiLog
import com.one.ui.banner.core.HiBannerMo
import com.one.ui.banner.indicator.HiCircleIndicator
import com.one.ui.banner.indicator.HiIndicator
import kotlinx.android.synthetic.main.banner_fragment.*
import kotlinx.android.synthetic.main.demo1_fragment.*
import java.sql.ParameterMetaData
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author  diaokaibin@gmail.com on 2021/11/17.
 */
class BannerFragment : HiBaseFragment() {

    private var urls = arrayOf(
        "https://www.devio.org/img/beauty_camera/beauty_camera1.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera3.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera4.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera5.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera2.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera6.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera7.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera8.jpeg"
    )

    private var autoPlay = false


    override fun getLayoutId(): Int {
        return R.layout.banner_fragment
    }


    override fun onResume() {
        super.onResume()


        val viewGroup = layoutView as ViewGroup
        val childCount = viewGroup.childCount
        HiLog.i(" childCount = " + childCount)

        for (i in 0..childCount) {


            viewGroup.getChildAt(i)


        }
        initView(HiCircleIndicator(context!!), false)
        auto_play.setOnCheckedChangeListener { buttonView, isChecked ->
            autoPlay = isChecked
            initView(HiCircleIndicator(context!!), autoPlay)
        }

        tv_switch.setOnClickListener {


        }

        btn_excel.setOnClickListener {

            startActivity(Intent(context, ExcelActivity::class.java))

        }

        btn_bank.setOnClickListener {

            startActivity(Intent(context, BankActivity::class.java))

        }

    }

    private fun initView(indicator: HiIndicator<*>, autoPlay: Boolean) {
        val moList: MutableList<HiBannerMo> = ArrayList()
        for (i in 0..7) {
            val mo = BannerMo()
            mo.url = urls[i % urls.size]
            moList.add(mo)
        }
        banner.setAutoPlay(autoPlay)
        banner.setIntervalTime(2000)
        banner.setHiIndicator(indicator)

        banner.setScrollDuration(500)
        // 自定义布局
        banner.setBannerData(R.layout.banner_item_layout, moList)
        banner.setBindAdapter { viewHolder, mo, pos ->


            val imageView: ImageView = viewHolder.findViewById(R.id.iv_image)
            Glide.with(this).load(mo.url).into(imageView)

            val titleView: TextView = viewHolder.findViewById(R.id.tv_title)
            titleView.text = mo.url

            HiLog.i(" pos = " + pos.toString() + " url : " + mo.url)


        }
    }
}


fun printType(param: Any) {
    println("$param is ${param::class.java.simpleName} type")
}