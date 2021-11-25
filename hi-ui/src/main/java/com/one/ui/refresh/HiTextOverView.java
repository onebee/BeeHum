package com.one.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.one.ui.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author diaokaibin@gmail.com on 2021/11/25.
 */
public class HiTextOverView extends HiOverView {

    private TextView textView;
    private ImageView imageView;

    public HiTextOverView(@NonNull Context context) {
        super(context);
    }

    public HiTextOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HiTextOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.hi_refresh_over_view, this, true);

        textView = findViewById(R.id.text);
        imageView = findViewById(R.id.iv_rotate);

    }

    @Override
    protected void onScroll(int scrollY, int pullRefreshHeight) {

    }

    @Override
    protected void onVisible() {

        textView.setText("下拉刷新");

    }

    @Override
    public void onOver() {

        textView.setText("松开刷新");
    }

    @Override
    public void onRefresh() {
        textView.setText("正在刷新");
        Animation loadAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_anim);
        LinearInterpolator interpolator = new LinearInterpolator();
        loadAnimation.setInterpolator(interpolator);
        imageView.startAnimation(loadAnimation);

    }

    @Override
    public void onFinish() {

        imageView.clearAnimation();
    }
}