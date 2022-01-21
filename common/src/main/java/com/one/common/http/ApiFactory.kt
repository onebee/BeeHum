package com.one.common.http

import com.one.common.http.api.HttpStatusInterceptor
import com.one.library.restful.HiRestful

/**
 * @author  diaokaibin@gmail.com on 2022/1/11.
 */
object ApiFactory {
    private const val baseUrl = "http://10.0.2.2:5088/"
    private val hiRestful = HiRestful(baseUrl, RetrofitCallFactory(baseUrl))

    init {
        hiRestful.addInterceptor(BizInterceptor())
        hiRestful.addInterceptor(HttpStatusInterceptor())
    }

    fun <T> create(service: Class<T>): T {
        return hiRestful.create(service)
    }
}