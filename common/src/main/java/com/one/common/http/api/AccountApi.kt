package com.one.common.http.api

import com.one.common.http.PersonBean
import com.one.common.http.model.CourseNotice
import com.one.common.http.model.UserProfile
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


    @POST("user/register")
    fun register(
        @Filed("imoocId") imoocId: String,
        @Filed("orderId") orderId: String,
        @Filed("userName") userName: String,
        @Filed("password") psd: String
    ): HiCall<String>

    @GET("user/profile")
    fun profile(): HiCall<UserProfile>


    @GET("notice")
    fun notice(): HiCall<CourseNotice>

}