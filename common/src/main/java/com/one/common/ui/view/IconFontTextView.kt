package com.one.common.ui.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * 用以支持全局的iconfont 资源的引用,可以在布局文件中直接设置 text
 * @author  diaokaibin@gmail.com on 2022/1/12.
 */
class IconFontTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        val typeface = Typeface.createFromAsset(context.assets, "/fonts/iconfont.ttf")
        setTypeface(typeface)
    }
}