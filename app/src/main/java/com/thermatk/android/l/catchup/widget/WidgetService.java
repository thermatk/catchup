package com.thermatk.android.l.catchup.widget;


import android.content.Intent;
import android.widget.RemoteViewsService;

import com.thermatk.android.l.catchup.NesEventsViewsFactory;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return(new NesEventsViewsFactory(this.getApplicationContext(),
                intent));
    }
}
