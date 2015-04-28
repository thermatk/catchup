package com.thermatk.android.l.catchup.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.orm.query.Select;
import com.thermatk.android.l.catchup.R;
import com.thermatk.android.l.catchup.data.NesNearestEvents;
import com.thermatk.android.l.catchup.widget.NesNearestEventsWidget;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class NesEventsViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context ctxt=null;
    private int appWidgetId;
    private String[] eventrows;

    public NesEventsViewsFactory(Context ctxt, Intent intent) {
        this.ctxt=ctxt;
        appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        Log.i("CatchUp", "Called widget update service start");
        List<NesNearestEvents> nesNearestEventsL = Select.from(NesNearestEvents.class).list();
        if(!nesNearestEventsL.isEmpty()) {
            NesNearestEvents nesNearestEvents = nesNearestEventsL.get(0);
            String events = nesNearestEvents.current.replaceAll("\n", "").replaceAll("&nbsp;","");
            Document doc = Jsoup.parseBodyFragment(events);
            Elements rows = doc.getElementsByTag("tr");
            eventrows = new String[rows.size()];
            int i=0;

            for (Element row : rows) {
                eventrows[i] = row.text();
                i++;
            }
        } else {
            eventrows = new String[1];
            eventrows[0] = "Пусто";
        }
    }

    @Override
    public void onDestroy() {
        // no-op
    }

    @Override
    public int getCount() {
        return(eventrows.length);
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row=new RemoteViews(ctxt.getPackageName(), R.layout.row);

        row.setTextViewText(android.R.id.text1, eventrows[position]);

        Intent i=new Intent();
        Bundle extras=new Bundle();

        extras.putString(NesNearestEventsWidget.EXTRA_WORD, eventrows[position]);
        i.putExtras(extras);
        row.setOnClickFillInIntent(android.R.id.text1, i);

        return(row);
    }

    @Override
    public RemoteViews getLoadingView() {
        return(null);
    }

    @Override
    public int getViewTypeCount() {
        return(1);
    }

    @Override
    public long getItemId(int position) {
        return(position);
    }

    @Override
    public boolean hasStableIds() {
        return(true);
    }

    @Override
    public void onDataSetChanged() {
        // no-op
    }
}
