package com.one.library.restful

import com.one.library.restful.annotation.*
import java.lang.IllegalStateException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @author  diaokaibin@gmail.com on 2021/12/29.
 */
class MethodParser(val baseUrl: String, method: Method, args: Array<Any>) {


    private  var domainUrl: String?=null

    /**
     * 是否是表单提交
     */
    private var formPost: Boolean = true
    private var httpMethod: Int = 0
    private lateinit var relativeUrl: String
    private var returnType: Type? = null

    private var headers: MutableMap<String, String> = mutableMapOf()
    private var paramters: MutableMap<String, String> = mutableMapOf()

    init {

        // parse method annotations such get,header,post,baseUrl
        parseMethodAnnotation(method)

        //parse method parameters such as path, filed
        parseMethodParameters(method, args)

        //parse method genric return type
        parseMethodReturnType(method)

    }

    /**
     * interface ApiService {
     *  @Headers("auth-token:token", "accountId:123456")
     *  @BaseUrl("https://api.devio.org/as/")
     *  @POST("/cities/{province}")
     *  @GET("/cities")
     * fun listCities(@Path("province") province: Int,@Filed("page") page: Int): HiCall<JsonObject>
     * }
     */
    private fun parseMethodReturnType(method: Method) {

        if (method.returnType != HiCall::class.java) {
            throw IllegalStateException(
                String.format(
                    "method %s is must be type of HiCall.class",
                    method.name

                )
            )

        }

        // 方法的泛型返回参数
        val genericReturnType = method.genericReturnType
        if (genericReturnType is ParameterizedType) {
            val actualTypeArguments = genericReturnType.actualTypeArguments
            require(actualTypeArguments.size == 1) {
                "method %s can only has one generic return type"
            }
            returnType = actualTypeArguments[0]

        } else {
            throw  IllegalStateException(
                String.format(
                    "method %s must has one generic",
                    method.name
                )
            )
        }
    }

    private fun parseMethodParameters(method: Method, args: Array<Any>) {
        //@Path("province") province: Int,@Filed("page") page: Int
        val parameterAnnotations = method.parameterAnnotations
        val equals = parameterAnnotations.size == args.size
        require(equals) {
            String.format(
                "arguments annotations count %s do'not match expect count %s ",
                parameterAnnotations.size,
                args.size
            )
        }

        //args
        for (index in args.indices) {
            // parameterAnnotations 是二维数组
            val annotations = parameterAnnotations[index]
            require(annotations.size <= 1) {
                "filed can only has one annotation : index =$index"
            }

            val value = args[index]
            require(isPrimitive(value)) {
                "8 basic types are supported for now,index=$index"
            }

            val annotation = annotations[0]
            if (annotation is Filed) {
                val key = annotation.value
                val value = args[index]
                paramters[key] = value.toString()
            } else if (annotation is Path) {
                val replaceName = annotation.value
                val replacement = value.toString()
                if (replaceName != null && replacement != null) {
                    val newRelativeUrl = relativeUrl.replace("{$replaceName}", replacement)
                    relativeUrl = newRelativeUrl

                }
            } else {
                throw IllegalStateException("cannot handle parameter annotation : " + annotation.javaClass.toString())
            }


        }


    }

    private fun isPrimitive(value: Any): Boolean {
        if (value.javaClass == String::class.java) {
            return true
        }

        try {
            // int byte short long boolean char double float
            val field = value.javaClass.getField("TYPE")
            val clazz = field[null] as Class<*>
            if (clazz.isPrimitive) {
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()

        }
        return false
    }

    private fun parseMethodAnnotation(method: Method) {
        val annotations = method.annotations
        for (an in annotations) {
            if (an is GET) {
                relativeUrl = an.value
                httpMethod = HiRequest.METHOD.GET
            } else if (an is POST) {
                relativeUrl = an.value
                httpMethod = HiRequest.METHOD.POST
                formPost = an.formPost
            } else if (an is Headers) {
                val headersArray: Array<out String> = an.value

                for (header in headersArray) {

                    val colon = header.indexOf(":")
                    check(!(colon == 0) || colon == -1) {
                        String.format(
                            "@headers value must be in the form [name:value],but found [%s]",
                            header
                        )
                    }
                    val name = header.substring(0, colon)
                    val value = header.substring(colon + 1).trim()
                    headers[name] = value
                }

            } else if (an is BaseUrl) {
                domainUrl = an.value
            } else {
                throw IllegalStateException("cannot handle method annotation : " + an.javaClass.toString())
            }
        }

        require(
            (httpMethod == HiRequest.METHOD.GET)
                    || (httpMethod == HiRequest.METHOD.POST
                    || (httpMethod == HiRequest.METHOD.PUT)
                    || (httpMethod == HiRequest.METHOD.DELETE))
        ) {
            String.format("method %s must has one of GET,POST,PUT,DELETE ", method.name)
        }

        if (domainUrl == null) {
            domainUrl = baseUrl
        }


    }

    fun newRequest(): HiRequest {
        var request = HiRequest()
        request.domainUrl = domainUrl
        request.returnType = returnType
        request.relativeUrl = relativeUrl
        request.parameters = paramters
        request.headers = headers
        request.httpMethod = httpMethod
        request.formPost = formPost
        return request
    }

    companion object {
        fun parse(baseUrl: String, method: Method, args: Array<Any>): MethodParser {
            return MethodParser(baseUrl, method, args)
        }
    }
}