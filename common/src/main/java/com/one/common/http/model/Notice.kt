package com.one.common.http.model

/**
 * @author  diaokaibin@gmail.com on 2022/1/27.
 * 		"id": "2",
"sticky": 1,
"type": "recommend",
"title": "好课推荐",
"subtitle": "解锁Flutter",
"url": "wwww",
"cover": "www",
"createTime": "2020-03-23 11:15:57"
 */
data class Notice(
    val id: String,
    val sticky: String,
    val type: String,
    val title: String,
    val subtitle: String,
    val url: String,
    val cover: String,
    val createTime: String
)
