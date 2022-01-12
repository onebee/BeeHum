package com.one.bee.http.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.one.bee.http.PersonBean
import com.one.library.restful.HiCall
import com.one.library.restful.annotation.Filed
import com.one.library.restful.annotation.GET


/**
 * @author  diaokaibin@gmail.com on 2022/1/11.
 */
 interface TestApi {

    @GET("wxarticle/chapters/json")
    fun listCities(@Filed("name") name: String): HiCall<List<PersonBean>>

}