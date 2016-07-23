package com.sam_chordas.android.stockhawk.widget;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.entities.QuoteModel;
import com.sam_chordas.android.stockhawk.ui.MyStocksActivity;

import java.util.ArrayList;
import java.util.List;

public class WidgetIntentService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public WidgetIntentService() {
        super("WidgetIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                QuoteWidgetProvider.class));

        // Perform this loop procedure for each Today widget
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_collection);

            Intent intent1 = new Intent(getApplicationContext(), StockWidgetService.class);
            intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

            // Add the data to the RemoteViews
            views.setRemoteAdapter(R.id.widget_list, intent1);
            views.setEmptyView(R.id.widget_list, R.id.empty_view);

            // Create an Intent to launch MainActivity
//            Intent launchIntent = new Intent(this, MyStocksActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
//            views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }
}
