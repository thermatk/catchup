package com.thermatk.android.l.catchup;

import org.apache.http.Header;

public interface CallbackListener{
    public void successCallback(int statusCode, Header[] headers, byte[] responseBody);
}
