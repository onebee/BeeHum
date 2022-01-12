package com.one.common.http

import com.one.library.restful.HiRestful

/**
 * @author  diaokaibin@gmail.com on 2022/1/11.
 */
object ApiFactory {
    private val baseUrl = "https://wanandroid.com/";
    private val hiRestful = HiRestful(baseUrl, RetrofitCallFactory(baseUrl))

    init {

        hiRestful.addInterceptor(BizInterceptor())
    }

    fun <T> create(service: Class<T>): T {
        return hiRestful.create(service)
    }
}