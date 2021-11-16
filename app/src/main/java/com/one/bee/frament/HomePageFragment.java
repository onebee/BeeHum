package com.one.bee.frament;

import android.widget.Toast;

import com.one.bee.R;
import com.one.common.ui.component.HiBaseFragment;
import com.one.ui.tab.common.IHiTabLayout;
import com.one.ui.tab.top.HiTabTopInfo;
import com.one.ui.tab.top.HiTabTopLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author diaokaibin@gmail.com on 2021/11/15.
 */
public class HomePageFragment extends HiBaseFragment {
    String[] tabsStr = new String[]{
            "热门",
            "服装",
            "数码",
            "鞋子",
            "零食",
            "家电",
            "汽车",
            "百货",
            "家居",
            "装修",
            "运动"
    };

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onStart() {
        super.onStart();

        initTabTop();
    }

    private void initTabTop() {
        HiTabTopLayout hiTabTopLayout = getActivity().findViewById(R.id.tab_top_layout);
        List<HiTabTopInfo<?>> infoList = new ArrayList<>();
        int defaultColor = getResources().getColor(R.color.tabBottomDefaultColor);
        int tintColor = getResources().getColor(R.color.tabBottomTintColor);
        for (String s : tabsStr) {
            HiTabTopInfo<?> info = new HiTabTopInfo<Integer>(s, defaultColor, tintColor);
            infoList.add(info);
        }
        hiTabTopLayout.inflateInfo(infoList);
        hiTabTopLayout.addTabSelectedChangeListener(new IHiTabLayout.OnTabSelectedListener<HiTabTopInfo<?>>() {
            @Override
            public void onTabSelectedChange(int index, @Nullable HiTabTopInfo<?> prevInfo, @NonNull HiTabTopInfo<?> nextInfo) {
                Toast.makeText(getActivity(), nextInfo.name, Toast.LENGTH_SHORT).show();
            }
        });
        hiTabTopLayout.defaultSelected(infoList.get(0));
    }
}
