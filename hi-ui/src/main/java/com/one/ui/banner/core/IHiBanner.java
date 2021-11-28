package com.one.ui.banner.core;

import com.one.ui.banner.indicator.HiIndicator;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * @author diaokaibin@gmail.com on 2021/11/28.
 */
public interface IHiBanner {

    void setBannerData(@LayoutRes int layoutResId, @NotNull List<? extends HiBannerMo> models);

    void setBannerData(@NonNull List<? extends HiBannerMo> models);

    void setHiIndicator(HiIndicator hiIndicator);

    void setAutoPlay(boolean autoPlay);

    void setIntervalTime(int intervalTime);

    void setBindAdapter(IBindAdapter bindAdapter);

    void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener);

    void setOnBannerClickListener(OnBannerClickListener onBannerClickListener);
    interface OnBannerClickListener {
        void onBannerClick(@NonNull HiBannerAdapter.HiBannerViewHolder viewHolder, @NonNull HiBannerMo bannerMo, int position);
    }
}
