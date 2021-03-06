package com.one.common.http

import com.one.library.log.HiLog
import com.one.library.restful.HiInterceptor

/**
 * @author  diaokaibin@gmail.com on 2022/1/11.
 */
class BizInterceptor: HiInterceptor {
    override fun intercept(chain: HiInterceptor.Chain): Boolean {

        if (chain.isRequestPeriod) {
            val request = chain.request()
            request.addHeader("token", " xxxxxxxxxxxxx")

            HiLog.dt("BizInterceptor",chain.request().headers)

        }else if (chain.response() != null) {

            HiLog.dt("BizInterceptor",chain.request().endPointUrl())
            HiLog.dt("BizInterceptor",chain.response()!!.rawData)
        }

        return false
    }
}