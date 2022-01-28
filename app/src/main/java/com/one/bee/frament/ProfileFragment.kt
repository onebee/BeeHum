package com.one.bee.frament

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import com.one.common.ui.component.HiBaseFragment
import com.one.bee.R
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.ImageView
import com.alibaba.android.arouter.launcher.ARouter
import com.one.common.http.ApiFactory
import com.one.common.http.api.AccountApi
import com.one.common.http.model.CourseNotice
import com.one.common.http.model.Notice
import com.one.common.http.model.UserProfile
import com.one.common.ui.view.loadCircle
import com.one.common.ui.view.loadCorner
import com.one.library.log.HiLog
import com.one.library.restful.HiCallback
import com.one.library.restful.HiResponse
import com.one.library.util.HiDisplayUtil
import com.one.ui.banner.core.HiBannerAdapter
import com.one.ui.banner.core.HiBannerMo
import kotlinx.android.synthetic.main.banner_fragment.*
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * @author diaokaibin@gmail.com on 2021/11/15.
 */
class ProfileFragment : HiBaseFragment() {
    private val ITME_PLACE_HOLDER = "   "
    private val REQUEST_CODE_LOGIN_PROFILE = 1001

    override fun getLayoutId(): Int {
        return R.layout.fragment_profile
    }

    override fun onResume() {
        super.onResume()
        queryLoginUser()
        queryCourseNotice()

        val list = arrayListOf<Notice>()
        list.add(Notice("1","one","recommend","推荐","Flutter","http://10.0.2.2:5088/swagger-ui.html#/","https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2F1114%2F062021132H5%2F210620132H5-1-1200.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1645971935&t=49100a3de7050f6089b068f7cff1c8e0","222"))
        list.add(Notice("1","two","hhh","推荐aa","Flutter22","http://10.0.2.2:5088/swagger-ui.html#/","https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2F4k%2Fs%2F02%2F2109242254305255-0-lp.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1645972573&t=ba64234e1cc526e8c4d0914012371abc","222"))
        val userProfile= UserProfile(true,10,5,"one",55,"https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2F4k%2Fs%2F02%2F21092422292J5A-0-lp.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1645971935&t=0636a6ba6bb50abd43b3a66211221b33",list)
        updateUI(userProfile)
    }

    private fun queryCourseNotice() {
        ApiFactory.create(AccountApi::class.java).notice()
            .enqueue(object : HiCallback<CourseNotice> {
                override fun onSuccess(response: HiResponse<CourseNotice>) {
                    if (response.data != null && response.data!!.total > 0) {
                        notify_count.text = response.data!!.total.toString()
                        notify_count.visibility = View.VISIBLE
                    }
                }

                override fun onFailed(throwable: Throwable) {
                    HiLog.e(throwable.message)
                }
            })


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        item_course.setText(R.string.if_notify)
        item_course.append(ITME_PLACE_HOLDER + getString(R.string.item_notify))

        notify_count.visibility = View.VISIBLE
        notify_count.text = 10.toString()

        item_collection.setText(R.string.if_collection)
        item_collection.append(ITME_PLACE_HOLDER + getString(R.string.item_collection))


        item_history.setText(R.string.if_history)
        item_history.append(ITME_PLACE_HOLDER + getString(R.string.item_history))

        item_address.setText(R.string.if_address)
        item_address.append(ITME_PLACE_HOLDER + getString(R.string.item_address))

    }

    private fun queryLoginUser() {
        ApiFactory.create(AccountApi::class.java).profile()
            .enqueue(object : HiCallback<UserProfile> {
                override fun onSuccess(response: HiResponse<UserProfile>) {

                    if (response.code == HiResponse.SUCCESS && response.data != null) {
                        val data: UserProfile = response.data!!
                        updateUI(data)
                    }
                }

                override fun onFailed(throwable: Throwable) {

                    HiLog.e(throwable.message)
                }


            })
    }

    private fun updateUI(userProfile: UserProfile) {
        user_name.text = if (userProfile.isLogin) userProfile.userName else "请先登录"
        login_desc.text = if (userProfile.isLogin) "欢迎回来" else "解锁全部功能,畅享架构师福利"

        if (userProfile.isLogin) {
            user_avatar.loadCircle(userProfile.avatar)
        } else {
            user_avatar.setImageResource(R.drawable.avatar)
            user_name.setOnClickListener {
                ARouter.getInstance().build("/account/login")
                    .navigation(activity, REQUEST_CODE_LOGIN_PROFILE)
            }
        }


        tab_item_collection.text = spannableTabItem(userProfile.favoriteCount, "\n收藏")
        tab_item_history.text = spannableTabItem(userProfile.browseCount, "\n历史浏览")
        tab_item_learning.text = spannableTabItem(userProfile.learnMinutes, "\n学习时长")

        updateBanner(userProfile.bannerNoticeList)
    }

    private fun updateBanner(bannerNoticeList: List<Notice>) {
        if (bannerNoticeList == null || bannerNoticeList.isEmpty()) return

        val modes = mutableListOf<HiBannerMo>()
        bannerNoticeList.forEach {
            val hiBannerMo = object : HiBannerMo() {}
            hiBannerMo.url = it.cover
            modes.add(hiBannerMo)

        }
        hi_banner.setBannerData(R.layout.layout_profile_banner_item, modes)
        hi_banner.setBindAdapter { viewHolder: HiBannerAdapter.HiBannerViewHolder, mo: HiBannerMo, pos: Int ->
            if (viewHolder == null || mo == null) return@setBindAdapter
            val iv = viewHolder.findViewById<ImageView>(R.id.banner_item_imageview)
            iv.loadCorner(mo.url, HiDisplayUtil.dp2px(10F, resources))
        }

        hi_banner.setOnBannerClickListener { viewHolder, bannerMo, position ->
//            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(bannerNoticeList[position].url))
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://10.0.2.2:5088/swagger-ui.html"))
            startActivity(intent)
        }

        hi_banner.visibility = View.VISIBLE
    }

    private fun spannableTabItem(topText: Int, bottomText: String): CharSequence? {

        val spanStr = topText.toString()
        val ssb = SpannableStringBuilder()
        var ssTop = SpannableString(spanStr)

        ssTop.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.color_000)),
            0,
            ssTop.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        ssTop.setSpan(AbsoluteSizeSpan(18, true), 0, ssTop.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        ssTop.setSpan(StyleSpan(Typeface.BOLD), 0, ssTop.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        ssb.append(ssTop)
        ssb.append(bottomText)
        return ssb
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_LOGIN_PROFILE && resultCode == Activity.RESULT_OK && data != null) {

            queryLoginUser()
        }
    }
}