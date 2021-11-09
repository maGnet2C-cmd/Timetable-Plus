package com.example.rmd;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.rmd.ui.deadlines.DeadlinesFragment;
import com.example.rmd.ui.list.ListFragment;
import com.example.rmd.ui.map.MapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class bottomNavigation extends AppCompatActivity {

    private BottomNavigationView navView;
    public static Context mConText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConText = this;
        setContentView(R.layout.activity_bottom_navigation);
        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_mapList, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        //change the status of dark mode dynamically
        SharedPreferences setting = getSharedPreferences("Setting", Activity.MODE_PRIVATE);
        if (setting.getBoolean("darkSys",true)) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }

    //change the status of dark mode dynamically
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences setting = getSharedPreferences("Setting", Activity.MODE_PRIVATE);
        if (setting.getBoolean("darkSys", true)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    //assign the title menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.title_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //assign the title menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.guide:
                SharedPreferences guide = getSharedPreferences("first", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = guide.edit();
                editor.putBoolean("status",true);
                editor.commit();
                Intent intent1 = new Intent(this, GuideActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //refresh the fragment data by detaching and attaching
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==1) {
            DeadlinesFragment.mFragment.getFragmentManager().beginTransaction().detach(DeadlinesFragment.mFragment).attach(DeadlinesFragment.mFragment).commit();
        } else if(resultCode==2) {
            ListFragment.mFragment.getFragmentManager().beginTransaction().detach(ListFragment.mFragment).attach(ListFragment.mFragment).commit();
        }
    }
}