package com.one.debug

/**
 * @author  diaokaibin@gmail.com on 2021/7/30.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class HiDebug (val name:String,val desc:String)