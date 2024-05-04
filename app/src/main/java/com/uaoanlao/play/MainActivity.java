package com.uaoanlao.play;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.uaoanlao.tv.ControlActicy;
import com.uaoanlao.tv.Screen;
import com.uaoanlao.tv.ScreenActivity;


public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout=findViewById(R.id.line1);

        Button an=findViewById(R.id.an);
        an.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Screen().setStaerActivity(MainActivity.this)
                        .setName("斗破苍穹")
                        .setUrl("https://s.xlzys.com/play/9avDmPgd/index.m3u8")
                        .setImageUrl("http://i0.hdslb.com/bfs/article/96fa4320db5115711c8c30afaff936910595d336.png")
                        .show();
                /*Intent detailIntent = new Intent(MainActivity.this, ScreenActivity.class);
                detailIntent.putExtra("name","斗破苍穹");
                detailIntent.putExtra("url","https://s.xlzys.com/play/9avDmPgd/index.m3u8");
                detailIntent.putExtra("ima","http://i0.hdslb.com/bfs/article/96fa4320db5115711c8c30afaff936910595d336.png");
                startActivity(detailIntent);*/
            }
        });
    }

}