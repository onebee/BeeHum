package com.one.bee.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.one.bee.R;
import com.one.library.log.HiLog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Handlerctivity extends AppCompatActivity {

    private Button buttonStart, buttonUpdate, buttonCancel;


    final int what = 666;
    Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            switch (msg.what) {

                case what:
                    HiLog.i(" 收到message");
                    handler.sendEmptyMessageDelayed(what, time);

                    break;
            }
            return false;
        }
    };

    Handler handler ;

    long time1 = 5000;
    long time2 = 10000;
   long time = time1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handlerctivity);
        handler = new Handler(getMainLooper(), callback);
        buttonStart = findViewById(R.id.btn_start);
        buttonUpdate = findViewById(R.id.btn_update);
        buttonCancel = findViewById(R.id.btn_cancel);
    }

    @Override
    protected void onResume() {
        super.onResume();

        handler.sendEmptyMessageDelayed(what, time1);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessageDelayed(what, time);
                HiLog.i(" 启动定时message");
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                time= time2;
                handler.removeMessages(what);

                handler.sendEmptyMessageDelayed(what, time);
                HiLog.i(" 更新定时message");
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                handler.removeMessages(what);
                HiLog.i(" 取消定时message");
            }
        });
    }
}