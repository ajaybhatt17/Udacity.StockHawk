package com.sam_chordas.android.stockhawk.entities;

import android.database.Cursor;

import com.sam_chordas.android.stockhawk.data.QuoteColumns;

public class QuoteModel {

    private String symbol;

    private String percentageChange;

    private String change;

    private String bidPrice;

    public QuoteModel() {

    }

    public QuoteModel(Cursor c) {
        if (c == null) return;
        symbol = c.getString(c.getColumnIndex(QuoteColumns.SYMBOL));
        bidPrice = c.getString(c.getColumnIndex(QuoteColumns.BIDPRICE));
        change = c.getString(c.getColumnIndex(QuoteColumns.CHANGE));
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(String percentageChange) {
        this.percentageChange = percentageChange;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof QuoteModel && symbol.equalsIgnoreCase(((QuoteModel)o).getSymbol());
    }
}
