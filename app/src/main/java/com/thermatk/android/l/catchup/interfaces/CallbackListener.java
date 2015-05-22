package com.thermatk.android.l.catchup.interfaces;

public interface CallbackListener{
    void successCallback(String cbMessage);
    void failCallback(String cbMessage);
    void viewStartLoading();
    void viewStopLoading();
}
