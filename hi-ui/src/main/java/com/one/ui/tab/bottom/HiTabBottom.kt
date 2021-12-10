package com.one.ui.tab.bottom

import android.widget.RelativeLayout
import com.one.ui.tab.common.IHiTab

/**
 * @author  diaokaibin@gmail.com on 2021/11/30.
 */
class HiTabBottom2 :IHiTab<HiTabBottomInfo<*>>{
    override fun setHiTabInfo(data: HiTabBottomInfo<*>?) {

    }

    override fun resetHeight(height: Int) {

    }

    override fun onTabSelectedChange(
        index: Int,
        prevInfo: HiTabBottomInfo<*>,
        nextInfo: HiTabBottomInfo<*>
    ) {
        TODO("Not yet implemented")
    }


}