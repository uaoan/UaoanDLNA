package com.uaoanlao.tv;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eliyar.bfdlna.DLNAManager;
import com.eliyar.bfdlna.DLNARequestCallBack;
import com.eliyar.bfdlna.SSDP.SSDPDevice;
import com.eliyar.bfdlna.Services.AVTransportManager;
import com.eliyar.bfdlna.Utils;

import java.io.IOException;
import java.util.HashMap;

public class ControlActicy extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
  
  public  SSDPDevice device;
  
  private TextView currentTimeLabel;
  private TextView totalDurationlabel;
  
  public Integer totalDuration = 0;
  public static String URL="";
  public AVTransportManager manager;
  public SeekBar seekBar;
  public AVTransportManager manager2;
  private boolean play=true;
  private ImageView imageView; //封面
  private TextView name; //标题
  private CountDownTimer countDownTimer;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_control_acticy);

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


    //Toast.makeText(this, getIntent().getCharSequenceExtra("name"), Toast.LENGTH_SHORT).show();
    Intent intent = getIntent();
    URL= getIntent().getStringExtra("url"); //设置播放链接

    // 设置要操作的设备
    device = (SSDPDevice) intent.getSerializableExtra("device");
    DLNAManager.getInstance().setCurrentDevice(device);
    //关闭界面
    LinearLayout end=findViewById(R.id.end);
    end.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        manager2.stop();
        countDownTimer.cancel();
        finish();
      }
    });

    //获取设备名称
    TextView title=findViewById(R.id.title);
    title.setText(getIntent().getCharSequenceExtra("title"));

    //设置重新选择设备事件
    LinearLayout title_layout=findViewById(R.id.title_layout);
    title_layout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(ControlActicy.this,ScreenActivity.class));
      }
    });

    //设置播放暂停按钮
    final ImageView image_play=findViewById(R.id.play);
    image_play.setImageResource(R.mipmap.zt);
    image_play.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (play){
          manager2.pause();
          image_play.setImageResource(R.mipmap.bf);
          play=false;
        }else {
          AVTransportManager manager3 = DLNAManager.getInstance().getAVTransportManager();
          manager2.play();
          image_play.setImageResource(R.mipmap.zt);
          play=true;
        }
      }
    });

    imageView=findViewById(R.id.image); //封面
    name=findViewById(R.id.name); //标题
    name.setText(getIntent().getCharSequenceExtra("name"));
    Glide.with(ControlActicy.this).load(getIntent().getCharSequenceExtra("ima")).into(imageView);


    seekBar = (SeekBar) findViewById(R.id.seekBar);
    currentTimeLabel   = (TextView) findViewById(R.id.left_Textview);
    totalDurationlabel = (TextView) findViewById(R.id.right_Textview);
    seekBar.setOnSeekBarChangeListener(this);
    //  manager = DLNAManager.getInstance().getAVTransportManager();
    manager2 = DLNAManager.getInstance().getAVTransportManager();
    
    manager = DLNAManager.getInstance().getAVTransportManager();

    //直接设置链接播放
    AVTransportManager manager1 = DLNAManager.getInstance().getAVTransportManager();
    manager.setCallBack(new DLNARequestCallBack() {
      @Override
      public void onFailure() {
        Log.e("ControlActicy", "onFailure");
      }

      @Override
      public void onSuccess(HashMap<String, String> info) {
        Log.d("ControlActicy", "onSuccess");
        getPosition();
      }
    });
    manager1.SetAVTransportURI(URL);

    //获取当前进度
    countDownTimer=new CountDownTimer(14400000, 1000) {
      @Override
      public void onTick(long millisUntilFinished) {
        getPosition();
      }

      @Override
      public void onFinish() {
        countDownTimer.cancel();
      }
    }.start();

/*  //播放投屏
    AVTransportManager manager1 = DLNAManager.getInstance().getAVTransportManager();

    manager.setCallBack(new DLNARequestCallBack() {
      @Override
      public void onFailure() {
        Log.e("ControlActicy", "onFailure");
      }

      @Override
      public void onSuccess(HashMap<String, String> info) {
        Log.d("ControlActicy", "onSuccess");
        getPosition();
      }
    });

    manager1.SetAVTransportURI(URL);

    //暂停
    manager2.pause();

    //停止
    manager2.stop();

    //播放
    AVTransportManager manager3 = DLNAManager.getInstance().getAVTransportManager();
      manager2.play()

      //播放位置
      getPosition();
    */


  }

  
  // 获取媒体进度
  private void getPosition() {
    AVTransportManager manager4 = DLNAManager.getInstance().getAVTransportManager();
    manager.setCallBack(new DLNARequestCallBack() {
      @Override
      public void onFailure() {
        Log.e("ControlActicy", "onFailure");
      }
      
      @Override
      public void onSuccess(final HashMap<String,String> info) {
        Log.d("ControlActicy", info.toString());
        
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            // 通过字典 info 获取一些信息
            currentTimeLabel.setText(info.get(AVTransportManager.REL_TIME));
            totalDurationlabel.setText(info.get(AVTransportManager.TRACK_DURATION));
            try {
              Integer realTime = Utils.convertStringToSecond(info.get(AVTransportManager.REL_TIME));
              Integer duration = Utils.convertStringToSecond(info.get(AVTransportManager.TRACK_DURATION));
              totalDuration = duration;
              int progress = (int) ((double) realTime / (double) duration * 100);
              Log.v("tttt", " " + progress);
              seekBar.setProgress(progress);
            }catch (Exception e){
            }

          }
        });
      }
    });
    manager.getPositionInfo();
  }
  
  public void onProgressChanged(SeekBar seekBar, int progress,
  boolean fromUser) {
    
  }
  
  public void onStartTrackingTouch(SeekBar seekBar) {}
  
  public void onStopTrackingTouch(SeekBar seekBar) {
    Log.v("ddd", "" + seekBar.getProgress());
    if (totalDuration != 0) {
      Integer target = (int) ((double) seekBar.getProgress() / 100 * (double) totalDuration);
      //   AVTransportManager manager = DLNAManager.getInstance().getAVTransportManager();
      manager.seek(target);
      getPosition(); //获取进度
    }
  }

  @Override
  public void onBackPressed() {
    AlertDialog tc=new AlertDialog.Builder(ControlActicy.this)
            .setTitle("提示")
            .setMessage("退出后是否继续播放投屏视频？")
            .setPositiveButton("取消播放", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                manager2.stop();
                countDownTimer.cancel();
                finish();
              }
            })
            .setNegativeButton("继续播放", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                countDownTimer.cancel();
                finish();
              }
            })
            .show();
         tc.getWindow().setBackgroundDrawableResource(R.drawable.alertdialogroun);

  }

  private int dp2px(float dpValue) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, this.getResources().getDisplayMetrics());
  }
}