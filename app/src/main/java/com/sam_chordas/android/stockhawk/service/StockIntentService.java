package com.sam_chordas.android.stockhawk.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.TaskParams;

/**
 * Created by sam_chordas on 10/1/15.
 */
public class StockIntentService extends IntentService {

  private Handler mHandler;

  public StockIntentService(){
    super(StockIntentService.class.getName());
  }

  public StockIntentService(String name) {
    super(name);
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    mHandler = new Handler();
    return super.onStartCommand(intent, flags, startId);
  }

  @Override protected void onHandleIntent(Intent intent) {
    Log.d(StockIntentService.class.getSimpleName(), "Stock Intent Service");
    final StockTaskService stockTaskService = new StockTaskService(this);
    Bundle args = new Bundle();
    if (intent.getStringExtra("tag").equals("add")){
      args.putString("symbol", intent.getStringExtra("symbol"));
    }
    // We can call OnRunTask from the intent service to force it to run immediately instead of
    // scheduling a task.
    int result = stockTaskService.onRunTask(new TaskParams(intent.getStringExtra("tag"), args));
    if (result== GcmNetworkManager.RESULT_FAILURE && stockTaskService.getFailureMsg()!=null) {
      mHandler.post(new Runnable() {
        @Override
        public void run() {
          Toast.makeText(StockIntentService.this, stockTaskService.getFailureMsg(), Toast.LENGTH_SHORT).show();
        }
      });
    }
  }
}
