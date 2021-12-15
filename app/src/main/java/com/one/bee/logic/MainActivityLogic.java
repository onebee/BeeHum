package com.one.bee.logic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.one.bee.R;
import com.one.bee.frament.CategoryFragment;
import com.one.bee.frament.FavoiteFragment;
import com.one.bee.frament.HomePageFragment;
import com.one.bee.frament.ProfileFragment;
import com.one.bee.frament.RecommendFragment;
import com.one.common.tab.HiFragmentTabView;
import com.one.common.tab.HiTabViewAdapter;
import com.one.library.util.HiDisplayUtil;
import com.one.ui.tab.bottom.HiTabBottomInfo;
import com.one.ui.tab.bottom.HiTabBottomLayout;
import com.one.ui.tab.common.IHiTabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;

/**
 * @author diaokaibin@gmail.com on 2021/11/15.
 * 将mainActivity 的一些逻辑内聚在这,让mainActivity 更加清爽
 */
public class MainActivityLogic {


    private HiFragmentTabView fragmentTabView;
    private HiTabBottomLayout hiTabBottomLayout;
    private static final String SAVED_CURRENT_ID = "SAVED_CURRENT_ID";
    private int currentItemIndex;

    /**
     * 代表activity 提供的一些能力
     */
    private ActivityProvider activityProvider;
    private List<HiTabBottomInfo<?>> infoList = new ArrayList<>();

    public MainActivityLogic(ActivityProvider activityProvider, Bundle savedInstanceState) {
        this.activityProvider = activityProvider;
        if (savedInstanceState != null) {
            currentItemIndex = savedInstanceState.getInt(SAVED_CURRENT_ID);
        }
        initTabBottom();
    }

    private void initTabBottom() {
        hiTabBottomLayout = activityProvider.findViewById(R.id.tab_layout);
        hiTabBottomLayout.setTabAlpha(0.55f);

        int defaultColor = activityProvider.getResources().getColor(R.color.tabBottomDefaultColor);
        int tintColor = activityProvider.getResources().getColor(R.color.tabBottomTintColor);

        Bitmap b1 = BitmapFactory.decodeResource(activityProvider.getResources(), R.drawable.home_default);
        Bitmap b2 = BitmapFactory.decodeResource(activityProvider.getResources(), R.drawable.home_selected);
        HiTabBottomInfo home = new HiTabBottomInfo<>(
                "home",
                b1,
                b2,
                defaultColor,
                tintColor

        );


        HiTabBottomInfo second = new HiTabBottomInfo<>(
                "second",
                b1,
                b2,
                defaultColor,
                tintColor

        );

        HiTabBottomInfo third = new HiTabBottomInfo<>(
                "third",
                b1,
                b2,
                defaultColor,
                tintColor

        );
        HiTabBottomInfo four = new HiTabBottomInfo<>(
                "four",
                b1,
                b2,
                defaultColor,
                tintColor
        );

        HiTabBottomInfo five = new HiTabBottomInfo<>(
                "five",
                b1,
                b2,
                defaultColor,
                tintColor
        );

        home.fragment = HomePageFragment.class;
        second.fragment = CategoryFragment.class;
        third.fragment = FavoiteFragment.class;
        four.fragment = RecommendFragment.class;
        five.fragment = ProfileFragment.class;

        this.infoList.add(home);
        this.infoList.add(second);
        this.infoList.add(third);
        this.infoList.add(four);
        this.infoList.add(five);

        initFragmentTabView();


        hiTabBottomLayout.inflateInfo(this.infoList);
        hiTabBottomLayout.addTabSelectedChangeListener(new IHiTabLayout.OnTabSelectedListener<HiTabBottomInfo<?>>() {
            @Override
            public void onTabSelectedChange(int index, @Nullable HiTabBottomInfo<?> prevInfo, @Nullable HiTabBottomInfo<?> nextInfo) {
                fragmentTabView.setCurrentItem(index);
                MainActivityLogic.this.currentItemIndex = index;
            }
        });
        hiTabBottomLayout.defaultSelected(infoList.get(currentItemIndex));

        hiTabBottomLayout.findTab(third).resetHeight(HiDisplayUtil.dp2px(55));
    }

    private void initFragmentTabView() {
        HiTabViewAdapter tabViewAdapter = new HiTabViewAdapter(infoList, activityProvider.getSupportFragmentManager());
        fragmentTabView = activityProvider.findViewById(R.id.fragment_tab_view);
        fragmentTabView.setAdapter(tabViewAdapter);
    }

    public void onSaveInstanceState(@NotNull Bundle outState) {
        outState.putInt(SAVED_CURRENT_ID, currentItemIndex);
    }

    public interface ActivityProvider {
        <T extends View> T findViewById(@IdRes int id);

        Resources getResources();

        FragmentManager getSupportFragmentManager();

        String getString(@StringRes int resId);
    }

    public HiFragmentTabView getFragmentTabView() {
        return fragmentTabView;
    }

    public HiTabBottomLayout getHiTabBottomLayout() {
        return hiTabBottomLayout;
    }

    public List<HiTabBottomInfo<?>> getInfoList() {
        return infoList;
    }
}
