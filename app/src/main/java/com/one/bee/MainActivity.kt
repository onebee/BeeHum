package com.one.bee

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.one.library.log.HiLog
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
        val hiTabBottomInfo = HiTabBottomInfo<String>(
            "home",
            b2,
            b1,
            "#ffd44949",
            "#ff656667",


        )

        tab_bottom.setHiTabInfo(hiTabBottomInfo)




    }
}