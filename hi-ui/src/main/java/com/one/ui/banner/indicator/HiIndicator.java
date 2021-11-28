package com.one.ui.banner.indicator;

import android.view.View;

/**
 * @author diaokaibin@gmail.com on 2021/11/28.
 * 指示器统一接口,实现该接口来定义需要的样式指示器
 */
public interface HiIndicator<T extends View> {
    T get();


    /**
     * 初始化 Indicator
     * @param count 幻灯片数量
     */
    void onInflate(int count);


    /**
     * 幻灯片切换回调
     * @param current 切换到的幻灯片位置
     * @param count   幻灯片数量
     */
    void onPointChange(int current, int count);


}
