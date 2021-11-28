package com.one.ui.banner.core;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

/**
 * @author diaokaibin@gmail.com on 2021/11/28.
 */
public class HiBannerAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }

    public abstract class HiBannerViewHolder{


    }
}