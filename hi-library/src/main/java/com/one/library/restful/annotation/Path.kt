package com.one.library.restful.annotation

/**
 * @GET("/cities/{province}")
 * fun test(@Path("province") int provinceId)
 * @author  diaokaibin@gmail.com on 2021/12/29.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Path(val value: String)
