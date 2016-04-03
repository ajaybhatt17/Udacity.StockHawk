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
import com.sam_chordas.android.stockhawk.ui.MyStocksActivity;

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

        Cursor c = getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                null, null, null, null);

        String symbol = "", bid_price = "", change = "";

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            symbol = c.getString(c.getColumnIndex(QuoteColumns.SYMBOL));
            bid_price = c.getString(c.getColumnIndex(QuoteColumns.BIDPRICE));
            change = c.getString(c.getColumnIndex(QuoteColumns.CHANGE));
        }
        if (c != null) c.close();

        // Perform this loop procedure for each Today widget
        for (int appWidgetId : appWidgetIds) {
            int layoutId = R.layout.widget_large;
            RemoteViews views = new RemoteViews(getPackageName(), layoutId);

            // Add the data to the RemoteViews
            views.setTextViewText(R.id.stock_symbol, symbol);
            views.setTextViewText(R.id.bid_price, bid_price);
            views.setTextViewText(R.id.change, change);

            // Create an Intent to launch MainActivity
            Intent launchIntent = new Intent(this, MyStocksActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }
}
