package com.thermatk.android.l.catchup.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.RemoteViews;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.thermatk.android.l.catchup.R;
import com.thermatk.android.l.catchup.data.NesUpdateTimes;
import com.thermatk.android.l.catchup.interfaces.CallbackListener;

import java.util.List;


/**
 * Implementation of App Widget functionality.
 */
public class NesNearestEvents extends AppWidgetProvider{
    public static String EXTRA_WORD=
            "com.thermatk.android.l.catchup.WORD";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        List<NesUpdateTimes> eventsUpdatedL = NesUpdateTimes.find(NesUpdateTimes.class, "type = ?", "NEARESTEVENTS");

        if(!eventsUpdatedL.isEmpty()) {
            if(eventsUpdatedL.get(0).needsUpdate(14300)) {
                context.startService(new Intent(context, WidgetUpdateService.class));
            } else {
                Log.i("CatchUp", Boolean.toString(eventsUpdatedL.get(0).needsUpdate(14300)));
            }
        } else {
            Log.i("CatchUp", "NOT UPDATED IN THE PAST");
            context.startService(new Intent(context, WidgetUpdateService.class));
        }

        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent svcIntent=new Intent(context, WidgetService.class);

        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews widget=new RemoteViews(context.getPackageName(),
                R.layout.widget);

        widget.setRemoteAdapter(R.id.words,
                svcIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, widget);
    }
}


