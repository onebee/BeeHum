package com.one.bee.pingan;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.one.bee.R;
import com.one.library.log.HiLog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;

import androidx.appcompat.app.AppCompatActivity;

public class PingAnActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    TextView tips;


    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_an);

        editText = findViewById(R.id.et_tips);
        button = findViewById(R.id.btn_op);
        tips = findViewById(R.id.tv_tips);

        context = this;
    }

    @Override
    protected void onResume() {
        super.onResume();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText.getText().toString();
                if (TextUtils.isEmpty(input)) {
                    Toast.makeText(PingAnActivity.this, "请输入命名前缀", Toast.LENGTH_SHORT).show();
                    return;
                }

                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                tips.setText("正在导入 请稍后 ... ");
                            }
                        });

                        getContentCallLog(input);

                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                tips.setText("导入完成 ... ");
                            }
                        });
                    }
                });

            }
        });
    }

    private String[] columns = {CallLog.Calls.CACHED_NAME// 通话记录的联系人
            , CallLog.Calls.NUMBER// 通话记录的电话号码
            , CallLog.Calls.DATE// 通话记录的日期
            , CallLog.Calls.DURATION// 通话时长
            , CallLog.Calls.TYPE};// 通话类型}

    private void getContentCallLog(String prefix) {

        Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, // 查询通话记录的URI
                columns
                , null, null, CallLog.Calls.DEFAULT_SORT_ORDER// 按照时间逆序排列，最近打的最先显示
        );
        HiLog.i("cursor count:" + cursor.getCount());
        long count = 0;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));  //姓名
            String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));  //号码
            long dateLong = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE)); //获取通话日期
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(dateLong));
            String time = new SimpleDateFormat("HH:mm").format(new Date(dateLong));
            int duration = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));//获取通话时长，值为多少秒
            int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE)); //获取通话类型：1.呼入2.呼出3.未接
            String dayCurrent = new SimpleDateFormat("dd").format(new Date());
            String dayRecord = new SimpleDateFormat("dd").format(new Date(dateLong));

            HiLog.i("Call log: " + "\n"
                    + "name: " + name + "\n"
                    + "date " + date + "\n"
                    + "phone number: " + number + "\n"
                    + "time: " + time + "\n"
                    + "duration: " + duration + "\n"
                    + "type: " + type + "\n"

            );
            count++;
            if (TextUtils.isEmpty(name) && (!number.startsWith("0")) &&(isToday(dateLong))) {
                addContract(number, type, prefix);
                long finalCount = count;
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        tips.setText("正在导入 " + finalCount + " /" + cursor.getCount());
                    }
                });
            }



        }

        //


    }

    public  boolean isToday(long time) {
        return isThisTime(time, "yyyy-MM-dd");
    }

    public  boolean isThisTime(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(date);//参数时间
        String now = sdf.format(new Date());//当前时间
        if (param.equals(now)) {
            return true;
        }
        return false;

    }

    private void addContract(String number, int type, String prefix) {
        ContentValues values = new ContentValues();
        Uri rawContactUri = getApplication().getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);

        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);

        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, prefix + type + "-" + number);
//        values.put(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, number);

//        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
//        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, number);
        getApplication().getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);


        //写入手机号码
        values.clear();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, number);
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        //插入data表
        Uri uri = Uri.parse("content://com.android.contacts/data");
        getApplication().getContentResolver().insert(uri, values);


    }


}