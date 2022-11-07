package com.one.bee.pingan;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.one.bee.R;
import com.one.common.ui.component.HiBaseActivity;
import com.one.library.log.HiLog;

public class ContactsActivity extends HiBaseActivity {


    EditText et;
    TextView tv;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);


        et = findViewById(R.id.et_prefix);
        tv = findViewById(R.id.tv_tips);
        btn = findViewById(R.id.btn_config);
    }

    int count;

    @Override
    protected void onStart() {
        super.onStart();

        count = 0;


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String input = et.getText().toString();
                if (!TextUtils.isEmpty(input)) {
                    delete(input);
                }
            }
        });
    }

    private void delete(String input) {
        Cursor cursor = null;
        try {
            //查询联系人数据,使用了getContentResolver().query方法来查询系统的联系人的数据
            //CONTENT_URI就是一个封装好的Uri，是已经解析过得常量
            cursor = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
            //对cursor进行遍历，取出姓名和电话号码
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    //获取联系人姓名
                    String displayName = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                    ));
                    //获取联系人手机号
                    String number = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    ));
                    //把取出的两类数据进行拼接，中间加换行符，然后添加到listview中
                    if (displayName.startsWith(input)) {
                        HiLog.d("删除" + displayName + number);
                        getContentResolver().delete(ContactsContract.Data.CONTENT_URI,
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "=?",
                                new String[]{displayName});


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv.append(" 删除 " + displayName + " " + number + " \n");
                            }
                        });
                        count++;
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btn.setText("全部 删除 " + count + " 个联系人");
                        }
                    });



                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //记得关掉cursor
            if (cursor != null) {
                cursor.close();
            }
        }

    }
}