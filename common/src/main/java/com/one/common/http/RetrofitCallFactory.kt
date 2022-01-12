package com.one.common.http

import com.one.library.restful.*
import okhttp3.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*
import java.lang.IllegalStateException

/**
 * @author  diaokaibin@gmail.com on 2022/1/7.
 */
class RetrofitCallFactory(val baseUrl: String) : HiCall.Factory {

    private var apiService: ApiService
    private var hiConvert: HiConvert

    init {
        val retrofit = Retrofit.Builder().baseUrl(baseUrl).build()
        apiService = retrofit.create(ApiService::class.java)
        hiConvert = GsonConvert()
    }

    override fun newCall(request: HiRequest): HiCall<Any> {
        return RetrofitCall(request)
    }

    internal inner class RetrofitCall<T>(private val request: HiRequest) : HiCall<T> {
        override fun execute(): HiResponse<T> {
            val realCall = createRealCall(request)
            val response = realCall.execute()
            return parseResponse(response)

        }

        override fun enqueue(callback: HiCallback<T>) {
            val realCall = createRealCall(request)
            realCall.enqueue(object : retrofit2.Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {

                    val response = parseResponse(response)
                    callback.onSuccess(response)

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    callback.onFailed(throwable = t)
                }


            })


        }


        private fun parseResponse(response: Response<ResponseBody>): HiResponse<T> {
            var rawData: String? = null
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    rawData = body.string()
                }
            } else {
                val errorBody = response.errorBody()
                if (errorBody != null) {
                    rawData = errorBody.string()
                }
            }
            return hiConvert.convert(rawData!!, request.returnType!!)
        }

        private fun createRealCall(request: HiRequest): Call<ResponseBody> {

            if (request.httpMethod == HiRequest.METHOD.GET) {
                return apiService.get(request.headers, request.endPointUrl(), request.parameters)
            } else if (request.httpMethod == HiRequest.METHOD.POST) {
                val parameters = request.parameters
                var builder = FormBody.Builder()
                var requestBody: RequestBody?
                var jsonObject = JSONObject()


                for ((key, value) in parameters!!) {
                    if (request.formPost) {

                        builder.add(key, value)

                    } else {
                        jsonObject.put(key, value)
                    }


                }

                if (request.formPost) {
                    requestBody = builder.build()

                } else {
                    // TODO : 待修改
//                    requestBody = ResponseBody.create(
//                        MediaType.parse("application/json;utf-8"),
//                        jsonObject.toString()
//                    )

                    requestBody=null
                }
                return apiService.post(request.headers, request.endPointUrl(), requestBody)

            } else {

                throw IllegalStateException("hirestful only support GET and POST now : " + request.endPointUrl())
            }


        }


    }

    interface ApiService {

        @GET
        fun get(
            @HeaderMap headers: MutableMap<String, String>?,
            @Url url: String,
            @QueryMap(encoded = true) params: MutableMap<String, String>?
        ): Call<ResponseBody>


        @POST
        fun post(
            @HeaderMap headers: MutableMap<String, String>?,
            @Url url: String,
            @Body body: RequestBody?
        ): Call<ResponseBody>

    }


}