package com.sam_chordas.android.stockhawk.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.entities.QuoteModel;

import java.util.ArrayList;
import java.util.List;

public class ListProvider implements RemoteViewsFactory {
    private List<QuoteModel> quoteModels = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetId;

    public ListProvider(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        populateList();
    }

    private void populateList() {
        quoteModels = new ArrayList<>();
        Cursor c = mContext.getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                null, null, null, null);

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            while (c.moveToNext()) {
                QuoteModel quoteModel = new QuoteModel(c);
                if (!quoteModels.contains(quoteModel)) quoteModels.add(quoteModel);
            }
        }
        if (c != null) c.close();
    }

    // Initialize the data set.
    public void onCreate() {
        // In onCreate() you set up any connections / cursors to your data source. Heavy lifting,
        // for example downloading or creating content etc, should be deferred to onDataSetChanged()
        // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return quoteModels.size();
    }

    // Given the position (index) of a WidgetItem in the array, use the item's text value in
    // combination with the app widget item XML file to construct a RemoteViews object.
    public RemoteViews getViewAt(int position) {
        // position will always range from 0 to getCount() - 1.

        // Construct a RemoteViews item based on the app widget item XML file, and set the
        // text based on the position.
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_collection_item);
        rv.setTextViewText(R.id.stock_symbol, quoteModels.get(position).getSymbol());
        rv.setTextViewText(R.id.change, quoteModels.get(position).getChange());


        // Return the RemoteViews object.
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
