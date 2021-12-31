package com.one.library.restful.annotation

/**
 * @author  diaokaibin@gmail.com on 2021/12/29.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DELETE(val value: String)
