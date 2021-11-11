package com.one.bee

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.one.library.log.HiLogManager
import com.one.library.log.HiViewPrinter
import com.one.library.util.HiDisplayUtil
import com.one.ui.tab.bottom.HiTabBottomInfo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var viewPrinter: HiViewPrinter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPrinter = HiViewPrinter(this)
        HiLogManager.getInstance().addPrinter(viewPrinter)

        viewPrinter!!.viewProvider.showFloatingView()

        val b1 = BitmapFactory.decodeResource(resources, R.drawable.home_default)
        val b2 = BitmapFactory.decodeResource(resources, R.drawable.home_selected)
        val home = HiTabBottomInfo<String>(
            "home",
            b1,
            b2,
            "#ff656667",
            "#ffd44949",

            )


        val second = HiTabBottomInfo<String>(
            "second",
            b1,
            b2,
            "#ff656667",
            "#ffd44949",

            )

        val third = HiTabBottomInfo<String>(
            "third",
            b1,
            b2,
            "#ff656667",
            "#ffd44949",

            )
        val four = HiTabBottomInfo<String>(
            "four",
            b1,
            b2,
            "#ff656667",
            "#ffd44949",
        )

        val five = HiTabBottomInfo<String>(
            "five",
            b1,
            b2,
            "#ff656667",
            "#ffd44949",
        )
        val list = mutableListOf<HiTabBottomInfo<*>>()
        list.add(home)
        list.add(second)
        list.add(third)
        list.add(four)
        list.add(five)

        tab_layout.setTabAlpha(0.75f)
        tab_layout.inflateInfo(list)

        tab_layout.addTabSelectedChangeListener { index, prevInfo, nextInfo ->

//            Toast.makeText(this@MainActivity, ""+nextInfo!!.name, Toast.LENGTH_SHORT).show()
        }
        tab_layout.defaultSelected(home)

        tab_layout.findTab(third).apply {
            resetHeight(HiDisplayUtil.dp2px(65f))
        }
    }
}