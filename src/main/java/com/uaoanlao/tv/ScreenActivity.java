package com.uaoanlao.tv;


import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.app.Activity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eliyar.bfdlna.DLNADeviceScanListener;
import com.eliyar.bfdlna.DLNAManager;
import com.eliyar.bfdlna.SSDP.SSDPDevice;

public class ScreenActivity extends AppCompatActivity implements  DLNADeviceScanListener {
  
  private DLNAManager manager;
  private ListView mListView;
  private ImageView end;
  private Button btnsearch;
  private CountDownTimer countDownTimer;
  
  //    private ArrayList<SSDPDevice> devices = new ArrayList<SSDPDevice>();
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_screen);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = getWindow();
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
              | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
      window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
              | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
              | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(Color.TRANSPARENT);
      window.setNavigationBarColor(Color.TRANSPARENT);
    }
    else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      Window window = getWindow();
      window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
              WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
    
    mListView = (ListView) findViewById(R.id.device_list_view);
    btnsearch=findViewById(R.id.btnSearch);
    btnsearch.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        manager.refreshDevices();
      }
    });

    end=findViewById(R.id.end);
    end.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });



    // 初始化 DLNA 单例
    manager = DLNAManager.getInstance();
    manager.start();
    manager.setScanDeviceListener(this);
    
    final DeviceListAdapter adapter = new DeviceListAdapter(this, manager.devices);
    mListView.setAdapter(adapter);

    final Context context = this;
    
    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SSDPDevice device= manager.devices.get(position);

        // 2
        Intent detailIntent = new Intent(context, ControlActicy.class);
        // 3
        detailIntent.putExtra("device", device);
        detailIntent.putExtra("title",device.friendlyName);
        detailIntent.putExtra("name",getIntent().getStringExtra("name"));
        detailIntent.putExtra("ima",getIntent().getStringExtra("ima"));
        detailIntent.putExtra("url",getIntent().getStringExtra("url"));
        // 4
        startActivity(detailIntent);
        countDownTimer.cancel();
        finish();
      }
    });


    //定时刷新
    countDownTimer=new CountDownTimer(10000000,1000) {
      @Override
      public void onTick(long millisUntilFinished) {
        manager.refreshDevices();
        adapter.notifyDataSetChanged();

      }

      @Override
      public void onFinish() {
        countDownTimer.cancel();
      }
    }.start();

  }
  
  private void startUDPServer() {
    
  }
  
  @Override
  public void didFoundDevice(SSDPDevice SSDPDevice) {
    final Context context = this;
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        DeviceListAdapter adapter = new DeviceListAdapter(context, manager.devices);
        mListView.setAdapter(adapter);
      }
    });
    
  }

  //重新开始
  @Override
  protected void onResume() {
    super.onResume();
    manager.refreshDevices();
  }


  //销毁
  @Override
  protected void onDestroy() {
    super.onDestroy();
    countDownTimer.cancel();
  }
}