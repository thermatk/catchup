package com.thermatk.android.l.catchup;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.thermatk.android.l.catchup.data.NesCourse;
import com.thermatk.android.l.catchup.data.NesUpdateTimes;
import com.thermatk.android.l.catchup.interfaces.CallbackListener;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.charset.Charset;
import java.util.List;

public class MyNesClient {
    private static final String BASE_URL = "https://my.nes.ru/";
    private final CallbackListener cListener;
    private final String username;
    private final String password;
    private boolean triedLogin;


    private AsyncHttpClient client = new AsyncHttpClient();

    public MyNesClient(Context appContext) {
        CallbackListener listener = (CallbackListener) appContext;
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

                    Element tablecourses = doc.getElementById("courses");
                    if (tablecourses != null) {
                        Elements courses = tablecourses.getElementsByTag("a");
                        for (Element course : courses) {
                            int mn_id = Integer.parseInt(course.parent().parent().getElementsByClass("dimmed").get(0).text());
                            String name = course.text();
                            if(Select.from(NesCourse.class).where(Condition.prop("my_nes_id").eq(mn_id)).count()==0) {
                                Log.i("CatchUp", "MYNES " + mn_id + " " + name);
                                NesCourse newcourse = new NesCourse(name, mn_id);
                                newcourse.save();
                            }
                        }
                        List<NesUpdateTimes> courseUpdatedL = NesUpdateTimes.find(NesUpdateTimes.class, "type = ?", "COURSELIST");
                        if(!courseUpdatedL.isEmpty()) {
                            courseUpdatedL.get(0).setUpdated();
                            courseUpdatedL.get(0).save();
                        } else {
                            NesUpdateTimes courseUpdated = new NesUpdateTimes("COURSELIST", 0);
                            courseUpdated.setUpdated();
                            courseUpdated.save();
                        }
                        cListener.successCallback("oooh yeah");
                    } else {
                        cListener.failCallback("MYNES html courses failed");
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("CatchUp", "MYNES FAILED COURSES CURRENT REQUEST" + statusCode);
                //Failed func
                cListener.failCallback("MYNES html courses failed r");
            }
        };
        get("adam.pl?student&lang=1", null, requestHandler);
    }

    public void getCourseDeadlines(final int myNesId) {
        AsyncHttpResponseHandler requestHandler = new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String resp = new String(responseBody, Charset.forName("CP1251"));
                Document doc = Jsoup.parse(resp);
                if(needsLogin(doc) && !triedLogin) {
                    triedLogin = true;
                    login(new Runnable() {
                        public void run() {
                            getCourseDeadlines(myNesId);
                        }
                    });
                } else {
                    Elements harows = doc.getElementById("has_table").getElementsByTag("tbody").first().getElementsByTag("tr");
                    if (harows.size() > 1) {
                        for (int i=1;i<harows.size();i++) {
                            Element harow= harows.get(i);
                            Log.i("CatchUp", harow.html());

                        }

                        //Elements homeassignments = hatable.get(1).getElementsByClass("dimmed");
                        /*Elements courses = tablecourses.getElementsByTag("a");
                        for (Element course : courses) {
                            int mn_id = Integer.parseInt(course.parent().parent().getElementsByClass("dimmed").get(0).text());
                            String name = course.text();
                            if(Select.from(NesCourse.class).where(Condition.prop("my_nes_id").eq(mn_id)).count()==0) {
                                Log.i("CatchUp", "MYNES " + mn_id + " " + name);
                                NesCourse newcourse = new NesCourse(name, mn_id);
                                newcourse.save();
                            }
                            }
                            */
                        cListener.successCallback("oooh yeah");
                    } else {
                        Log.i("CatchUp", "MYNES Course General failed");
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("CatchUp", "MYNES FAILED Course General REQUEST" + statusCode);
                //Failed func
            }
        };
        get("adam.pl?student/courses/crs&cid="+Integer.toString(myNesId)+"&pane=homeworks&lang=1", null, requestHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
    private boolean needsLogin(Document code) {
        return code.getElementById("persist") != null && code.getElementById("persist").attr("name").equals("persistent");
    }
}
