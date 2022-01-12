package com.one.library.restful

import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap

/**
 * @author  diaokaibin@gmail.com on 2021/12/29.
 */
open class HiRestful constructor(val baseUrl: String, val callFactory: HiCall.Factory) {
    private var interceptors: MutableList<HiInterceptor> = mutableListOf()
    private var methodService: ConcurrentHashMap<Method, MethodParser> = ConcurrentHashMap()
    private var scheduler: Scheduler

    init {
        scheduler = Scheduler(callFactory, interceptors)
    }

    fun addInterceptor(interceptor: HiInterceptor) {
        interceptors.add(interceptor)
    }

    fun <T> create(service: Class<T>): T {
        return Proxy.newProxyInstance(
            service.classLoader,
            arrayOf<Class<*>>(service)
        ) { proxy, method, args ->

            // 拿出method 对象 解析 该方法上的所有信息
            var methodParser = methodService[method]
            if (methodParser == null) {
                methodParser = MethodParser.parse(baseUrl, method, args)
                methodService[method] = methodParser
            }

            val request = methodParser.newRequest()
            // 返回一个代理的call 对象
            scheduler.newCall(request)

        } as T
    }
}