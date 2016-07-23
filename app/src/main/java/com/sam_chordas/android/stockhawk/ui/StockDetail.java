package com.sam_chordas.android.stockhawk.ui;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.db.chart.Tools;
import com.db.chart.model.LineSet;
import com.db.chart.view.AxisController;
import com.db.chart.view.LineChartView;
import com.google.gson.Gson;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.entities.ResponseModel;
import com.sam_chordas.android.stockhawk.entities.ResponseModel.QuoteModel;
import com.sam_chordas.android.stockhawk.entities.ResponseModel.ResultsModel;
import com.sam_chordas.android.stockhawk.rest.Utils;

import java.io.IOException;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StockDetail extends AppCompatActivity {

    @Bind(R.id.linechart)
    protected LineChartView lineChartView;

    @Bind(R.id.text)
    protected TextView chartDescp;

    @Bind(R.id.content_loading)
    protected ContentLoadingProgressBar contentLoadingProgressBar;

    private LineSet mCloseLineSet = new LineSet();
    private LineSet mOpenLineSet = new LineSet();
    private String stockName;

    private static final long DAY_TIME = 24 * 60 * 60 * 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("symbol")) {
            stockName = extras.getString("symbol");
            setTitle(String.format("Detail : %1$s", stockName));
            long currentTime = new Date().getTime() - (DAY_TIME);
            long sixmonthBefore = new Date().getTime() - (120 * DAY_TIME);
            loadGraph(stockName, Utils.getDate(sixmonthBefore), Utils.getDate(currentTime));
        } else {
            Toast.makeText(StockDetail.this, getString(R.string.display_error_msg), Toast.LENGTH_SHORT).show();
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadGraph(String stockName, final String startDate, final String endDate) {
        new AsyncTask<String, String, ResultsModel>() {

            @Override
            protected ResultsModel doInBackground(String... params) {
                try {
                    String s = Utils.fetchData(Utils.getStockDetailUrl(params[0], params[1], params[2]));
                    ResponseModel responseModel = new Gson().fromJson(s, ResponseModel.class);
                    return responseModel.getData().getResults();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ResultsModel resultsModel) {
                super.onPostExecute(resultsModel);
                if (resultsModel != null) {
                    resultsModel.traverse();
                    for (QuoteModel quoteModel : resultsModel.getQuotes()) {
                        mCloseLineSet.addPoint(quoteModel.getDate(), quoteModel.getClose());
                        mOpenLineSet.addPoint(quoteModel.getDate(), quoteModel.getOpen());
                    }
                    mOpenLineSet.setThickness(Tools.fromDpToPx(2))
                            .setSmooth(true).setColor(Color.parseColor("#b3b5bb"))
                            .setFill(Color.parseColor("#2d374c"));
                    mCloseLineSet.setThickness(Tools.fromDpToPx(2))
                            .setSmooth(true).setColor(Color.parseColor("#758cbb"))
                            .setFill(Color.parseColor("#2d374c"));
                    lineChartView.addData(mOpenLineSet);
                    lineChartView.addData(mCloseLineSet);
                    lineChartView.setBorderSpacing(Tools.fromDpToPx(15))
                            .setAxisBorderValues(resultsModel.getLowerGraphLimit(), resultsModel.getUpperGraphLimit(), resultsModel.getStep())
                            .setXLabels(AxisController.LabelPosition.NONE)
                            .setLabelsColor(Color.parseColor("#6a84c3"))
                            .setXAxis(false)
                            .setYAxis(true);
                    lineChartView.show();
                    chartDescp.setText(String.format(getString(R.string.stock_detail_msg), Utils.getDateLocale(startDate), Utils.getDateLocale(endDate)));
                    contentLoadingProgressBar.hide();
                    lineChartView.setVisibility(View.VISIBLE);
                } else {
                    contentLoadingProgressBar.hide();
                    Toast.makeText(StockDetail.this, getString(R.string.error_msg), Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(stockName, startDate, endDate);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
