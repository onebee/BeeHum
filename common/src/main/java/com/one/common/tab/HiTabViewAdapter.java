package com.one.common.tab;

import android.view.View;

import com.one.ui.tab.bottom.HiTabBottomInfo;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * @author diaokaibin@gmail.com on 2021/11/15.
 */
public class HiTabViewAdapter {

    private List<HiTabBottomInfo<?>> infoList;


    private Fragment currentFragment;

    public Fragment getCurrentFragment() {
        return currentFragment;
    }


    private FragmentManager fragmentManager;


    public HiTabViewAdapter(List<HiTabBottomInfo<?>> infoList, FragmentManager fragmentManager) {
        this.infoList = infoList;
        this.fragmentManager = fragmentManager;
    }

    public void instantiateItem(View container, int position) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        String name = container.getId() + ":" + position;
        Fragment fragment = fragmentManager.findFragmentByTag(name);
        if (fragment != null) {
            transaction.show(fragment);
        } else {
            fragment = getItem(position);
            if (!fragment.isAdded()) {
                transaction.add(container.getId(), fragment, name);
            }
        }

        this.currentFragment = fragment;
        transaction.commitAllowingStateLoss();
    }

    public Fragment getItem(int position) {
        try {
            return infoList.get(position).fragment.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCount() {
        return infoList == null ? 0 : infoList.size();
    }
}
