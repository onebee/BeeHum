package com.one.bee.frament;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.one.bee.R;
import com.one.bee.top.BannerFragment;
import com.one.bee.top.CrashDemoFragment;
import com.one.bee.top.Demo10Fragment;
import com.one.bee.top.Demo11Fragment;
import com.one.bee.top.Demo3Fragment;
import com.one.bee.top.Demo8Fragment;
import com.one.bee.top.Demo9Fragment;
import com.one.bee.top.HiARouterFragment;
import com.one.bee.top.HiExecutorSamplerFragment;
import com.one.bee.top.HttpFragment;
import com.one.bee.top.RefreshFragment;
import com.one.common.ui.component.HiBaseFragment;
import com.one.library.log.HiLog;
import com.one.ui.tab.common.IHiTabLayout;
import com.one.ui.tab.top.HiTabTopInfo;
import com.one.ui.tab.top.HiTabTopLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author diaokaibin@gmail.com on 2021/11/15.
 */
public class HomePageFragment extends HiBaseFragment {
    String[] tabsStr = new String[]{
            "轮播",
            "下拉刷新",
            "RecyclerView",
            "协程",
            "网络框架",
            "ARouter",
            "汽车",
            "百货",
            "Retrofit",
            "OkHttp",
            "View"
    };

    Class[] fs = new Class[]{
            BannerFragment.class,
            RefreshFragment.class,
            Demo3Fragment.class,
            HiExecutorSamplerFragment.class,
            HttpFragment.class,
            HiARouterFragment.class,
            CrashDemoFragment.class,
            Demo8Fragment.class,
            Demo9Fragment.class,
            Demo10Fragment.class,
            Demo11Fragment.class,
    };

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home1;
    }

    @Override
    public void onStart() {
        super.onStart();

        initTabTop();
    }

    private void initTabTop() {
        HiTabTopLayout hiTabTopLayout = getActivity().findViewById(R.id.tab_top_layout);
        FrameLayout fl = getActivity().findViewById(R.id.ll_container);
        List<HiTabTopInfo<?>> infoList = new ArrayList<>();
        int defaultColor = getResources().getColor(R.color.tabBottomDefaultColor);
        int tintColor = getResources().getColor(R.color.tabBottomTintColor);
        for (int i = 0; i < tabsStr.length; i++) {

            String s = tabsStr[i];
            HiTabTopInfo<?> info = new HiTabTopInfo<Integer>(s, defaultColor, tintColor);
            infoList.add(info);

            info.fragment = fs[i];
        }
        hiTabTopLayout.inflateInfo(infoList);
        hiTabTopLayout.addTabSelectedChangeListener(new IHiTabLayout.OnTabSelectedListener<HiTabTopInfo<?>>() {
            @Override
            public void onTabSelectedChange(int index, @Nullable HiTabTopInfo<?> prevInfo, @NonNull HiTabTopInfo<?> nextInfo) {
                Toast.makeText(getActivity(), nextInfo.name, Toast.LENGTH_SHORT).show();

                try {
                    Fragment fragment = nextInfo.fragment.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(fl.getId(), fragment, nextInfo.name).commitAllowingStateLoss();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });
        hiTabTopLayout.defaultSelected(infoList.get(0));

        Thread thread = new Thread();
        thread.start();




    }

    @Override
    public void onResume() {
        super.onResume();

        SubThread subThread = new SubThread("SUB THREAD");

        subThread.start();


        Handler handler = new Handler(subThread.getLooper()){

            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                HiLog.i("handleMessage : "+ msg.what);
                HiLog.i( "handleMessage : " + Thread.currentThread().getName());
            }
        };

        handler.sendEmptyMessage(6666666);

    }

    class SubThread extends Thread{

        private Looper looper;

        public SubThread(String thread) {
            super(thread);
        }


        @Override
        public void run() {
            super.run();

            Looper.prepare();
            synchronized (this) {
                looper = Looper.myLooper();
                notifyAll();
            }

            Looper.loop();


        }

        public Looper getLooper() {
            synchronized (this) {
                if (looper == null && isAlive()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return looper;
        }
    }

    private void initFragmentTabView() {

    }
}
