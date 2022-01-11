package com.one.library.restful

import androidx.annotation.IntDef
import com.one.library.restful.annotation.DELETE
import com.one.library.restful.annotation.PUT
import java.lang.IllegalStateException
import java.lang.reflect.Type

open class HiRequest {

    /**
     * 返回请求的完整Url
     *
     *  * //scheme-host-port:443
    //https://api.devio.org/v1/ ---relativeUrl: user/login===>https://api.devio.org/v1/user/login

    //可能存在别的域名的场景
    //https://api.devio.org/v2/


    //https://api.devio.org/v1/ ---relativeUrl: /v2/user/login===>https://api.devio.org/v2/user/login
     */
    fun endPointUrl(): String {
        if (relativeUrl == null) {
            throw IllegalStateException("relative url must be not null")
        }
        if (!relativeUrl!!.startsWith("/")) {
            return domainUrl + relativeUrl
        }
        val indexOf = domainUrl!!.indexOf("/")
        return domainUrl!!.substring(0, indexOf) + relativeUrl
    }

    fun addHeader(name: String, value: String) {

        if (headers == null) {
            headers= mutableMapOf()
        }
        headers!![name] = value
    }

    var formPost: Boolean=true

    @METHOD
    var httpMethod = 0
    var headers: MutableMap<String, String>? = null
    var parameters: MutableMap<String, String>? = null
    var domainUrl: String? = null
    var relativeUrl: String? = null
    var returnType: Type? = null

    @IntDef(value = [METHOD.GET, METHOD.POST, METHOD.PUT, METHOD.DELETE])
    annotation class METHOD {
        companion object {
            const val GET = 0
            const val POST = 1
            const val PUT = 2
            const val DELETE = 3
        }
    }

}
