package com.one.library.restful

import java.lang.reflect.Type

/**
 * @author  diaokaibin@gmail.com on 2022/1/11.
 */
interface HiConvert {

    fun <T> convert(rawData: String, dataType: Type): HiResponse<T>
}

