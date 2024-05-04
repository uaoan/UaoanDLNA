package com.uaoanlao.tv;

import android.app.Activity;
import android.content.Intent;

public class Screen {
    private Intent detailIntent;
    private Activity context;
    public Screen setStaerActivity(Activity activity){
        context=activity;
        detailIntent = new Intent(context, ScreenActivity.class);
        return this;
    }
    public Screen show(){
        context.startActivity(detailIntent);
        return this;
    }
    public Screen setName(String nr){
        detailIntent.putExtra("name",nr);
        return this;
    }
    public Screen setUrl(String nr){
        detailIntent.putExtra("url",nr);
        return this;
    }
    public Screen setImageUrl(String nr){
        detailIntent.putExtra("ima",nr);
        return this;
    }

}
