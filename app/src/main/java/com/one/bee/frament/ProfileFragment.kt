package com.one.bee.frament

import com.one.common.ui.component.HiBaseFragment
import com.one.bee.R
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * @author diaokaibin@gmail.com on 2021/11/15.
 */
class ProfileFragment : HiBaseFragment() {
    private val ITME_PLACE_HOLDER = "   "


    override fun getLayoutId(): Int {
        return R.layout.fragment_profile
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        item_course.setText(R.string.if_notify)
        item_course.append(ITME_PLACE_HOLDER + getString(R.string.item_notify))

        notify_count.visibility=View.VISIBLE
        notify_count.text = 10.toString()

        item_collection.setText(R.string.if_collection)
        item_collection.append(ITME_PLACE_HOLDER + getString(R.string.item_collection))


        item_history.setText(R.string.if_history)
        item_history.append(ITME_PLACE_HOLDER + getString(R.string.item_history))

        item_address.setText(R.string.if_address)
        item_address.append(ITME_PLACE_HOLDER + getString(R.string.item_address))

    }
}