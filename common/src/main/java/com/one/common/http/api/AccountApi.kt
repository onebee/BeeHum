package com.one.common.http.api

import com.one.common.http.PersonBean
import com.one.library.restful.HiCall
import com.one.library.restful.annotation.Filed
import com.one.library.restful.annotation.GET
import com.one.library.restful.annotation.POST


/**
 * @author  diaokaibin@gmail.com on 2022/1/11.
 */
interface AccountApi {

    @POST("user/login")
    fun login(
        @Filed("userName") name: String,
        @Filed("password") psd: String
    ): HiCall<String>

}