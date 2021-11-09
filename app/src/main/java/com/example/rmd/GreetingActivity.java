package com.example.rmd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class GreetingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences setting = getSharedPreferences("Setting", Activity.MODE_PRIVATE);
        int delay;
        if(setting.getBoolean("greeting",true)) {
            delay = 500;
        } else {
            delay = 0;
        }
        handler.sendEmptyMessageDelayed(0, delay);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getHome();
            super.handleMessage(msg);
        }
    };

    public void getHome() {
        //Initiate deadlines SP
        SharedPreferences sp = getSharedPreferences("activity", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (!sp.contains("key")) {
            editor.putString("key", "cd_00001");
            editor.commit();
        }
        //Initiate timetable SP
        SharedPreferences tt = getSharedPreferences("timetable", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = tt.edit();
        if (!tt.contains("key")) {
            editor1.putString("key", "tt_00001");
            editor1.commit();
        }

        Intent intent = new Intent(this, GuideActivity.class);
        startActivity(intent);
        finish();
    }
}