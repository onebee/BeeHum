package com.one.bee.top

import android.widget.Toast
import com.one.bee.R
import com.one.common.ui.component.HiBaseFragment
import com.one.library.log.HiLog
import com.one.ui.refresh.HiRefresh
import com.one.ui.refresh.HiTextOverView
import kotlinx.android.synthetic.main.demo1_fragment.*
import kotlinx.android.synthetic.main.fragment_refresh.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.logging.Handler

/**
 * @author  diaokaibin@gmail.com on 2021/11/17.
 */
class RefreshFragment : HiBaseFragment() {


    override fun getLayoutId(): Int {
        return R.layout.fragment_refresh
    }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
    override fun onResume() {
        super.onResume()

        val overView = HiTextOverView(context!!)
        hi_refresh_layout.setRefreshOverView(overView)

        hi_refresh_layout.setDisableRefreshScroll(false)

        hi_refresh_layout.setRefreshListener(object : HiRefresh.HiRefreshListener {
            override fun onRefresh() {
                android.os.Handler().postDelayed({
                    hi_refresh_layout.refreshFinished()
                }, 10000)
            }

            override fun enableRefresh(): Boolean {
                return true
            }
        })
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receiveMsg(msg: String) {
        HiLog.i(" RefreshFragment receive $msg")
        Toast.makeText(context, "receive = $msg" , Toast.LENGTH_SHORT).show()
    }

}