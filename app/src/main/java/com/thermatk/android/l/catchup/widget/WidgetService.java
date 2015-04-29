package com.thermatk.android.l.catchup.widget;


import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.i("CatchUp", "in the WidgetService");
        return(new NesEventsViewsFactory(this.getApplicationContext(),
                intent));
    }
}
