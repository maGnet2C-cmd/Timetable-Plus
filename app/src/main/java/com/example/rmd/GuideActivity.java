package com.example.rmd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener {

    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    private AppCompatActivity appCompatActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initViews();
    }

    //initialize the ViewPager and assign guide page layouts to this ViewPager
    private void initViews() {
        SharedPreferences guide = getSharedPreferences("first", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = guide.edit();
        Boolean isFirst = guide.getBoolean("status",true);
        if(!isFirst) {
            Intent intent = new Intent(this, bottomNavigation.class);
            startActivity(intent);
            finish();
        }
        editor.putBoolean("status",false);
        editor.commit();

        LayoutInflater inflater = LayoutInflater.from(this);
        ConstraintLayout guide7 = (ConstraintLayout) inflater.inflate(R.layout.guide7, null);
        guide7.findViewById(R.id.floatingActionButton5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this, bottomNavigation.class);
                startActivity(intent);
                finish();
            }
        });
        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.guide1, null));
        views.add(inflater.inflate(R.layout.guide2, null));
        views.add(inflater.inflate(R.layout.guide3, null));
        views.add(inflater.inflate(R.layout.guide4, null));
        views.add(inflater.inflate(R.layout.guide5, null));
        views.add(inflater.inflate(R.layout.guide6, null));
        views.add(guide7);
        vpAdapter = new ViewPagerAdapter(views, this.appCompatActivity);
        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(vpAdapter);
        vp.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}