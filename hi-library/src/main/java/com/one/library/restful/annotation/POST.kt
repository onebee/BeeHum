package com.one.library.restful.annotation

/**
 * @author  diaokaibin@gmail.com on 2021/12/29.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class POST(val value:String,val formPost:Boolean = true)
