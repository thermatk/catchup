package com.thermatk.com.thermatk.android.l.catchup;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.nio.charset.Charset;

public class MyNesClient {
    private static final String BASE_URL = "https://my.nes.ru/";
    private PersistentCookieStore nesCookieStore;
    private CallbackListener cListener;
    private String username;
    private String password;


    private AsyncHttpClient client = new AsyncHttpClient();

    public MyNesClient(Context appContext, CallbackListener listener) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(appContext);
        username = sharedPrefs.getString("nesusername", "NULL");
        password = sharedPrefs.getString("nespassword", "NULL");
        cListener = listener;
        nesCookieStore = new PersistentCookieStore(appContext);
        client.setCookieStore(nesCookieStore);
    }

    public void get(String url, RequestParams params) {
        client.get(getAbsoluteUrl(url), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                cListener.successCallback(statusCode, headers, responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("CatchUp", "MYNES FAILED REQUEST" + statusCode);
            }
        });
    }

    private void post(String url, RequestParams params) {
        client.post(getAbsoluteUrl(url), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String resp = new String(responseBody, Charset.forName("CP1251"));
                Log.i("CatchUp", "MYNES HTML FROM REQUEST" + resp);
                //cListener.successCallback(statusCode, headers, responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("CatchUp", "MYNES FAILED REQUEST" + statusCode);
            }
        });
    }

    public void login() {
        RequestParams params = new RequestParams();
        params.put("login", username);
        params.put("password", password);
        params.put("persistent", "on");
        params.put("go", "guest/login");
        params.put("query", "");
        this.post("adam.pl", params);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
