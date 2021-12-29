package com.one.library.restful

/**
 * @author  diaokaibin@gmail.com on 2021/12/29.
 */
interface HiInterceptor {

    fun intercept(chain: Chain): Boolean

    /**
     * Chain 对象会在 派发拦截器的时候 创建
     *
     */
    interface Chain {
        /**
         * 是不是在网络发起之前
         */
        val isRequestPeriod: Boolean get() = false

        fun request(): HiRequest

        /**
         * response 对象在网络发起之前是空的
         */
        fun response(): HiResponse<*>?
    }
}