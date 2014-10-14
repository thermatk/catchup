package com.thermatk.android.l.catchup;

import org.apache.http.Header;

public interface CallbackListener{
    public void successCallback(String cbMessage);
    public void failCallback(String cbMessage);
}
