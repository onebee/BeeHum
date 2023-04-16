package com.one.bee.frament;

import android.os.Handler;
import android.os.Message;
import android.os.Trace;

import androidx.annotation.NonNull;

/**
 * @author diaokaibin@gmail.com on 2023/4/15.
 */
public class Test {
  public static void main(String[] args) {
    Trace.beginSection("aaa");


    Handler handler = new Handler(new Handler.Callback() {
      @Override
      public boolean handleMessage(@NonNull Message msg) {
        return false;
      }
    });

    Trace.endSection();
  }
} 