package com.sam_chordas.android.stockhawk.entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResponseModel {

    @SerializedName("query")
    private QueryModel data;

    public QueryModel getData() {
        return data;
    }

    public void setData(QueryModel data) {
        this.data = data;
    }


    public class QueryModel {

        private int count;
        private String created;
        private String lang;
        private ResultsModel results;

        public ResultsModel getResults() {
            return results;
        }

        public void setResults(ResultsModel results) {
            this.results = results;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public class ResultsModel {

        @SerializedName("quote")
        private List<QuoteModel> quotes = new ArrayList<>();

        private float minCloseValue = 0.0f;
        private float maxCloseValue = 0.0f;
        private int step = 2;
        private int upperGraphLimit = 0;
        private int lowerGraphLimit = 0;

        public List<QuoteModel> getQuotes() {
            return quotes;
        }

        public void setQuotes(List<QuoteModel> quotes) {
            this.quotes = quotes;
        }

        public float getMaxCloseValue() {
            return maxCloseValue;
        }

        public void setMaxCloseValue(float maxCloseValue) {
            this.maxCloseValue = maxCloseValue;
        }

        public float getMinCloseValue() {
            return minCloseValue;
        }

        public void setMinCloseValue(float minCloseValue) {
            this.minCloseValue = minCloseValue;
        }

        public int getMinCloseValue(int near) {
            if (near == 0) return (int) minCloseValue;
            return ((int) (minCloseValue / near)) * near;
        }

        public int getMaxCloseValue(int near) {
            if (near == 0) return (int) maxCloseValue;
            return ((int) maxCloseValue / near) * near;
        }

        public int getStep() {
            return step;
        }

        public void traverse() {
            for (QuoteModel quoteModel : quotes) {
                if (quoteModel.getClose() < minCloseValue || minCloseValue == 0.0f) {
                    minCloseValue = quoteModel.getClose();
                }
                if (quoteModel.getClose() > maxCloseValue || maxCloseValue == 0.0f) {
                    maxCloseValue = quoteModel.getClose();
                }
            }
            step = (int) (((maxCloseValue - minCloseValue) * 2) / 15);
            if (step == 0) step = 2;
            float diff = maxCloseValue - minCloseValue;
            upperGraphLimit = (int) ((maxCloseValue + (diff / 2)) / step) * step;
            lowerGraphLimit = (int) ((minCloseValue - (diff / 2)) / step) * step;
        }

        public void setStep(int step) {
            this.step = step;
        }

        public int getUpperGraphLimit() {
            return upperGraphLimit;
        }

        public void setUpperGraphLimit(int upperGraphLimit) {
            this.upperGraphLimit = upperGraphLimit;
        }

        public int getLowerGraphLimit() {
            return lowerGraphLimit;
        }

        public void setLowerGraphLimit(int lowerGraphLimit) {
            this.lowerGraphLimit = lowerGraphLimit;
        }
    }

    public class QuoteModel {

        @SerializedName("Symbol")
        private String symbol;

        @SerializedName("Date")
        private String date;

        @SerializedName("Open")
        private float open;

        @SerializedName("High")
        private float high;

        @SerializedName("Low")
        private float low;

        @SerializedName("Close")
        private float close;

        @SerializedName("Volume")
        private int volume;

        @SerializedName("Adj_Close")
        private float adjClose;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public float getOpen() {
            return open;
        }

        public void setOpen(float open) {
            this.open = open;
        }

        public float getHigh() {
            return high;
        }

        public void setHigh(float high) {
            this.high = high;
        }

        public float getLow() {
            return low;
        }

        public void setLow(float low) {
            this.low = low;
        }

        public float getClose() {
            return close;
        }

        public void setClose(float close) {
            this.close = close;
        }

        public int getVolume() {
            return volume;
        }

        public void setVolume(int volume) {
            this.volume = volume;
        }

        public float getAdjClose() {
            return adjClose;
        }

        public void setAdjClose(float adjClose) {
            this.adjClose = adjClose;
        }

    }
}

