package com.one.bee.arouter

import android.content.Context
import android.widget.Toast
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import java.lang.RuntimeException

/**
 * @author  diaokaibin@gmail.com on 2022/1/20.
 */
@Route(path = "biz_interceptor")
class BizInterceptor : IInterceptor {

    private var context: Context? = null
    override fun init(context: Context?) {
        this.context = context
    }

    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        val flag = postcard!!.extra
        if ((flag and (RouteFlag.FLAG_LOGIN)) != 0) {
            callback!!.onInterrupt(RuntimeException("need login"))
            showToast("请先登录")
        } else if ((flag and (RouteFlag.FLAG_AUTHENTICATION)) != 0) {
            callback!!.onInterrupt(RuntimeException("need login"))
            showToast("请先实名认证")
        } else if ((flag and (RouteFlag.FLAG_VIP)) != 0) {
            callback!!.onInterrupt(RuntimeException("need become vip"))
            showToast("请先加入会员")
        } else {
            callback!!.onContinue(postcard)
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}