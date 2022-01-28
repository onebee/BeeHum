package com.one.common.http.model

/**
 * @author  diaokaibin@gmail.com on 2022/1/27.
 */
data class UserProfile(
    val isLogin: Boolean,
    val favoriteCount: Int,
    val browseCount: Int,
    val userName: String,
    val learnMinutes: Int,
    val avatar: String,
    var bannerNoticeList: List<Notice>
) {


}

