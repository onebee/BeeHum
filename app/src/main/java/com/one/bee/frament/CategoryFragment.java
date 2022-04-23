package com.one.bee.frament;


import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.one.bee.R;
import com.one.bee.pingan.PingAnActivity;
import com.one.common.ui.component.HiBaseFragment;

/**
 * @author diaokaibin@gmail.com on 2021/11/15.
 */
public class CategoryFragment extends HiBaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_category;
    }


    @Override
    public void onStart() {
        super.onStart();
        Button button = getActivity().findViewById(R.id.btn_puhui);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PingAnActivity.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
