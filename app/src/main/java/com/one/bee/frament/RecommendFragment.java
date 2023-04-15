package com.one.bee.frament;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.one.bee.R;
import com.one.bee.sample.HandlerSampleActivity;
import com.one.common.ui.component.HiBaseFragment;

/**
 * @author diaokaibin@gmail.com on 2021/11/15.
 */
public class RecommendFragment extends HiBaseFragment {


    Button btnHandler,btnPlayer;

    @Override
    public int getLayoutId() {

        return R.layout.fragment_recommond;
    }

    @Override
    public void onStart() {
        super.onStart();
        btnHandler = getActivity().findViewById(R.id.btn_handler);
        btnHandler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HandlerSampleActivity.class);
                startActivity(intent);
            }
        });

        btnPlayer = getActivity().findViewById(R.id.btn_player);
        btnPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), PlayerActivity.class);
//                startActivity(intent);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();


    }
}
