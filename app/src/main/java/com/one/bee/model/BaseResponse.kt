package com.one.bee.model

/**
 * @author  diaokaibin@gmail.com on 2023/4/1.
 */
 class BaseResponse<T>(val data: List<T>, val errorCode: Int, val errorMsg: String)