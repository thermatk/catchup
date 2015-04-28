package com.thermatk.android.l.catchup.widget;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.thermatk.android.l.catchup.MyNesClient;
import com.thermatk.android.l.catchup.interfaces.CallbackListener;

public class WidgetUpdateService extends Service implements CallbackListener {
    public WidgetUpdateService() {
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        getInfo();
        return super.onStartCommand(intent, flags, startId);
    }

    public void getInfo() {
        MyNesClient myNes = new MyNesClient(this, getApplicationContext());
        myNes.getNearestEvents();
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void successCallback(String cbMessage) {
        Log.i("CatchUp", "SuccessUpdatedEventsWidget");
        stopSelf();

    }

    @Override
    public void failCallback(String cbMessage) {
        Log.i("CatchUp", "FailUpdatedEventsWidget");
        stopSelf();

    }

    @Override
    public void viewStartLoading() {

    }

    @Override
    public void viewStopLoading() {

    }
}
