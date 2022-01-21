package com.one.bee

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.one.bee.logic.MainActivityLogic
import com.one.common.ui.component.HiBaseActivity
import com.one.library.log.HiLogManager
import com.one.library.log.HiViewPrinter

class MainActivity : HiBaseActivity(), MainActivityLogic.ActivityProvider {

    var viewPrinter: HiViewPrinter? = null
    var activityLogic: MainActivityLogic? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityLogic = MainActivityLogic(this, savedInstanceState)

        viewPrinter = HiViewPrinter(this)
        HiLogManager.getInstance().addPrinter(viewPrinter)

        viewPrinter!!.viewProvider.showFloatingView()




    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        activityLogic!!.onSaveInstanceState(outState);

    }
}