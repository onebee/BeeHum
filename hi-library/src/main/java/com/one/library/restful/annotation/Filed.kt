package com.one.library.restful.annotation

/**
 * @author  diaokaibin@gmail.com on 2021/12/29.
 * @BaseUrl("xxx")
 * fun test(@Filed("province" int pro)
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Filed(val value:String)
