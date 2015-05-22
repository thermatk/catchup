package com.thermatk.android.l.catchup.widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.thermatk.android.l.catchup.MyNesClient;
import com.thermatk.android.l.catchup.R;
import com.thermatk.android.l.catchup.interfaces.CallbackListener;

public class WidgetUpdateService extends Service implements CallbackListener {
    Intent myIntent;
    public WidgetUpdateService() {
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        myIntent = intent;
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

        RemoteViews remoteViews =new RemoteViews(getApplicationContext().getPackageName(), R.layout.widget);
        AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
        ComponentName thisWidget = new ComponentName(getApplicationContext(),  NesNearestEventsWidget.class);
        int[] allWidgetIds = manager.getAppWidgetIds(thisWidget);

        Intent intent = new Intent(this, NesNearestEventsWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);
        sendBroadcast(intent);

        for (int widgetId : allWidgetIds) {
            Log.i("CatchUp", "SuccessUpdatedEventsWidget" + widgetId);
            manager.updateAppWidget(widgetId, remoteViews);

            manager.notifyAppWidgetViewDataChanged(widgetId, R.id.words);
        }
        stopIt();

    }

    @Override
    public void failCallback(String cbMessage) {
        Log.i("CatchUp", "FailUpdatedEventsWidget");
        stopIt();
    }

    public void stopIt() {
        stopForeground(true);
        stopService(myIntent);
        stopSelf();
    }

    @Override
    public void viewStartLoading() {

    }

    @Override
    public void viewStopLoading() {

    }
}
