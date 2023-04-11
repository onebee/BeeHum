package com.one.bee.utils

import android.content.res.Resources
import android.util.TypedValue

/**
 * @author  diaokaibin@gmail.com on 2023/4/9.
 */
val Float.px
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this, Resources.getSystem().displayMetrics
    )

