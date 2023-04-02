package com.one.bee.sample.concurrent;

import android.os.AsyncTask;

/**
 * @author diaokaibin@gmail.com on 2021/12/24.
 */
public class ConcurrentTest {

    public static void main(String[] args) {

        System.out.println("hahahhahahhhh");


        class MyAsyncTask extends AsyncTask<String, Integer, String> {
            @Override
            protected String doInBackground(String... strings) {
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);

            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
    }


} 