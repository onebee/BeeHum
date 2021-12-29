package com.one.library.restful

import java.io.IOException

/**
 * @author  diaokaibin@gmail.com on 2021/12/29.
 */
interface HiCall<T> {

    @Throws(IOException::class)
    fun execute(): HiResponse<T>
    fun enqueue(callback: HiCallback<T>)

    interface Factory{
        fun newCall(request:HiRequest):HiCall<*>
    }
}