package com.one.bee.top

import com.one.bee.R
import com.one.common.ui.component.HiBaseFragment
import com.one.ui.refresh.HiRefresh
import com.one.ui.refresh.HiTextOverView
import kotlinx.android.synthetic.main.demo1_fragment.*
import kotlinx.android.synthetic.main.fragment_refresh.*
import java.util.logging.Handler

/**
 * @author  diaokaibin@gmail.com on 2021/11/17.
 */
class RefreshFragment : HiBaseFragment() {


    override fun getLayoutId(): Int {
        return R.layout.fragment_refresh
    }


    override fun onResume() {
        super.onResume()

        val overView = HiTextOverView(context!!)
        hi_refresh_layout.setRefreshOverView(overView)


        hi_refresh_layout.setRefreshListener(object : HiRefresh.HiRefreshListener {
            override fun onRefresh() {
                android.os.Handler().postDelayed({
                    hi_refresh_layout.refreshFinished()
                }, 1000)
            }

            override fun enableRefresh(): Boolean {
                return true
            }
        })
    }


}