package com.one.ui.banner.core;

import android.content.Context;
import android.widget.Scroller;

/**
 * @author diaokaibin@gmail.com on 2021/11/30.
 */
public class HiBannerScroller extends Scroller {

    /**
     * 值越大,滚动越慢
     */
    private int duration = 1000;

    public HiBannerScroller(Context context, int duration) {
        super(context);
        this.duration = duration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, this.duration);

    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, this.duration);
    }
}