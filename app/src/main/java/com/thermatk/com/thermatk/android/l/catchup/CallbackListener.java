package com.thermatk.com.thermatk.android.l.catchup;

import org.apache.http.Header;

/**
 * Created by thermatk on 10.10.14.
 */
public interface CallbackListener{
    public void successCallback(int statusCode, Header[] headers, byte[] responseBody);
}
