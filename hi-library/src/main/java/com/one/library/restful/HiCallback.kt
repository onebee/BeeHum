package com.one.library.restful

/**
 * @author  diaokaibin@gmail.com on 2021/12/29.
 */
interface HiCallback<T> {
    fun onSuccess(response: HiResponse<T>)
    fun onFailed(throwable: Throwable)
}