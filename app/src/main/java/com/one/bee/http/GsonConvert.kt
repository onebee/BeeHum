package com.one.bee.http

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.one.library.restful.HiConvert
import com.one.library.restful.HiResponse
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type

/**
 * @author  diaokaibin@gmail.com on 2022/1/11.
 */
class GsonConvert : HiConvert {

    private var gson: Gson

    init {
        gson = Gson()
    }

    override fun <T> convert(rawData: String, dataType: Type): HiResponse<T> {
        var response: HiResponse<T> = HiResponse<T>()
        try {
            var jsonObject = JSONObject(rawData)
            response.code = jsonObject.optInt("code")
            response.msg = jsonObject.optString("msg")
            val data: String = jsonObject.optString("data")

            if (response.code == HiResponse.SUCCESS) {
                response.data = gson.fromJson(data, dataType)

            } else {
                // json 数据转换成hashMap 标准写法
                response.errorData = gson.fromJson<MutableMap<String, String>>(
                    data,
                    object : TypeToken<MutableMap<String, String>>() {
                    }.type
                )
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            response.code = -1
            response.msg = e.message
        }
        response.rawData = rawData
        return response
    }
}