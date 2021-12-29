package com.one.library.restful.annotation

/**
 * @author  diaokaibin@gmail.com on 2021/12/29.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Path(val value: String)
