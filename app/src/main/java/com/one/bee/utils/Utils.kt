package com.one.bee.utils

import android.content.res.Resources
import android.util.TypedValue

/**
 * @author  diaokaibin@gmail.com on 2023/4/9.
 */
class Utils {

    companion object {
        fun dp2px(value: Float): Float {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, value,
                Resources.getSystem().displayMetrics
            )

        }
    }

}