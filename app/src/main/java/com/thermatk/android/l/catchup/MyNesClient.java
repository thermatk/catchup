package com.thermatk.android.l.catchup;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.nio.charset.Charset;

class MyNesClient {
    private static final String BASE_URL = "https://my.nes.ru/";
    private final CallbackListener cListener;
    private final String username;
    private final String password;
    private boolean triedLogin;


    private AsyncHttpClient client = new AsyncHttpClient();

    public MyNesClient(Context appContext, CallbackListener listener) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(appContext);
        username = sharedPrefs.getString("nesusername", "NULL");
        password = sharedPrefs.getString("nespassword", "NULL");
        cListener = listener;
        triedLogin = false;
        client.setCookieStore(new PersistentCookieStore(appContext));
    }

    private void get(String url, RequestParams params, AsyncHttpResponseHandler reqHandler) {

        client.get(getAbsoluteUrl(url), params, reqHandler);
    }



    private void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private void login(final Runnable successRunnable) {
        RequestParams params = new RequestParams();
        params.put("login", username);
        params.put("password", password);
        params.put("persistent", "on");
        params.put("go", "guest/login");
        params.put("query", "");
        this.post("adam.pl", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //add check login again and wrong credentials
                successRunnable.run();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("CatchUp", "MYNES FAILED LOGIN REQUEST" + statusCode);
                //Failed login
            }
        });
    }

    public void getNearestEvents() {
        AsyncHttpResponseHandler requestHandler = new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String resp = new String(responseBody, Charset.forName("CP1251"));
                Document doc = Jsoup.parse(resp);
                if(needsLogin(doc) && !triedLogin) {
                    triedLogin = true;
                    login(new Runnable() {
                        public void run() {
                            getNearestEvents();
                        }
                    });
                } else {

                    Elements table7 = doc.select("td.right_col table.table7");
                    if (table7.size() > 1) {
                        String found = table7.get(1).html();
                        cListener.successCallback(found);
                    } else {
                        Log.i("CatchUp", "MYNES html courses failed");
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("CatchUp", "MYNES FAILED NEAREST EVENTS REQUEST" + statusCode);
                //Failed func
            }
        };
        get("adam.pl?student&lang=1", null, requestHandler);
    }

    public void getCurrentCourseList() {
        AsyncHttpResponseHandler requestHandler = new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String resp = new String(responseBody, Charset.forName("CP1251"));
                Document doc = Jsoup.parse(resp);
                if(needsLogin(doc) && !triedLogin) {
                    triedLogin = true;
                    login(new Runnable() {
                        public void run() {
                            getCurrentCourseList();
                        }
                    });
                } else {

                    Elements table7 = doc.select("table#courses");
                    if (true) {
                        String found = table7.html();
                        cListener.successCallback(found);
                    } else {
                        Log.i("CatchUp", "MYNES html courses failed");
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("CatchUp", "MYNES FAILED NEAREST EVENTS REQUEST" + statusCode);
                //Failed func
            }
        };
        get("adam.pl?student&lang=1", null, requestHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
    private boolean needsLogin(Document code) {
        return code.getElementById("persist") != null && code.getElementById("persist").attr("name").equals("persistent");
    }
}
