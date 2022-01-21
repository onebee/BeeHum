package com.one.bee.arouter

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.DegradeService
import com.alibaba.android.arouter.launcher.ARouter

/**
 * 全局降级服务,当路由的时候 目标页面不存在, 统一定向到这里
 * @author  diaokaibin@gmail.com on 2022/1/21.
 */
@Route(path = "/degrade/global/service")
class DegradeServiceImpl : DegradeService {
    override fun init(context: Context?) {

    }

    override fun onLost(context: Context?, postcard: Postcard?) {
        //Green channel, it will skip all of interceptors.
        ARouter.getInstance().build("/degrade/global/activity").greenChannel().navigation()
    }
}