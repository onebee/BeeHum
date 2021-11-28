package com.one.ui.banner;

import android.content.Context;

import com.one.ui.banner.core.HiBannerMo;
import com.one.ui.banner.core.IBindAdapter;
import com.one.ui.banner.core.IHiBanner;
import com.one.ui.banner.indicator.HiIndicator;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * @author diaokaibin@gmail.com on 2021/11/28.
 * HiBanner 的控制器
 * 辅助HiBanner 完成各种功能的控制
 * 将HiBanner 的一些逻辑内聚在这,保证暴露给使用者的 HiBanner 干净整洁
 */
public class HiBannerDelegate implements IHiBanner {
    private Context context;
    private HiBanner hiBanner;

    public HiBannerDelegate(Context context, HiBanner banner) {
        this.context = context;
        this.hiBanner = banner;
    }

    @Override
    public void setBannerData(int layoutResId, @NonNull List<? extends HiBannerMo> models) {

    }

    @Override
    public void setBannerData(@NonNull List<? extends HiBannerMo> models) {

    }

    @Override
    public void setHiIndicator(HiIndicator<?> hiIndicator) {

    }

    @Override
    public void setAutoPlay(boolean autoPlay) {

    }

    @Override
    public void setIntervalTime(int intervalTime) {

    }

    @Override
    public void setLoop(boolean loop) {

    }

    @Override
    public void setBindAdapter(IBindAdapter bindAdapter) {

    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {

    }

    @Override
    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {

    }
}