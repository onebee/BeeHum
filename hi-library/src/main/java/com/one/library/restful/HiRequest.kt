package com.one.library.restful

import androidx.annotation.IntDef
import com.one.library.restful.annotation.DELETE
import com.one.library.restful.annotation.PUT
import java.lang.reflect.Type

open class HiRequest {

    @METHOD
    var httpMethod = 0
    var headers: MutableMap<String, String>? = null
    var parameters: MutableMap<String, Any>? = null
    var domainUrl: String? = null
    var relativeUrl: String? = null
    var returnType: Type? = null

    @IntDef(value = [METHOD.GET, METHOD.POST,METHOD.PUT,METHOD.DELETE])
    internal annotation class METHOD {
        companion object {
            const val GET = 0
            const val POST = 1
            const val PUT = 2
            const val DELETE = 3
        }
    }

}
