package com.one.common.http.api

import com.alibaba.android.arouter.launcher.ARouter
import com.one.library.restful.HiInterceptor
import com.one.library.restful.HiResponse

/**
 * 根据 response 的 code 自动路由到相关页面
 * @author  diaokaibin@gmail.com on 2022/1/21.
 */
class HttpStatusInterceptor : HiInterceptor {
    override fun intercept(chain: HiInterceptor.Chain): Boolean {
        val response = chain.response()
        if (!chain.isRequestPeriod && response != null) {

            when (response.code) {
                HiResponse.RC_NEED_LOGIN -> {
                    ARouter.getInstance().build("/account/login").navigation()
                }

                //token 过期
                HiResponse.RC_AUTH_TOKEN_EXPIRED , HiResponse.RC_AUTH_TOKEN_INVALID ,HiResponse.RC_USER_FORBID -> {

                    var helpUrl: String? = null
                    if (response.errorData != null) {
                        helpUrl = response.errorData!!["helpUrl"]
                    }

                    ARouter.getInstance().build("/degrade/global/activity")
                        .withString("degrade_title", "非法访问")
                        .withString("degrade_desc", response.msg)
                        .withString("degrade_action", helpUrl)
                        .navigation()
                }
            }
        }


        return false

    }


}