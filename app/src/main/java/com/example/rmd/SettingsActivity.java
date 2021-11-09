 package com.example.rmd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.SweepGradient;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.chip.ChipDrawable;

import java.util.Calendar;
import java.util.Date;

public class SettingsActivity extends Activity {

    private Switch greetingSwitch;
    private Switch darkSysSwitch;
    private Switch notiSwitch;
    private Spinner spinner_ddl;
    private String data_hour;
    private String data_min;
    private Calendar c;
    private TextView timeText;
    private alarmManagerUtils alarmUtils;
    private String ddlDays;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final SharedPreferences setting = getSharedPreferences("Setting", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = setting.edit();

        data_hour=setting.getString("hour", "21");
        data_min=setting.getString("min", "00");
        timeText = findViewById(R.id.textView10);
        timeText.setText(data_hour+":"+data_min);

        greetingSwitch = findViewById(R.id.setting_greeting);
        if (setting.getBoolean("greeting", true)) {
            greetingSwitch.setChecked(true);
        } else {
            greetingSwitch.setChecked(false);
        }
        greetingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("greeting", true);
                } else {
                    editor.putBoolean("greeting", false);
                }
                editor.commit();
            }
        });

        darkSysSwitch = findViewById(R.id.setting_darkSys);
        if (setting.getBoolean("darkSys", true)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            darkSysSwitch.setChecked(true);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            darkSysSwitch.setChecked(false);
        }
        darkSysSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (Build.VERSION.SDK_INT >= 29) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                        editor.putBoolean("darkSys", true);
                    } else {
                        darkSysSwitch.setChecked(false);
                        editor.putBoolean("darySys", false);
                        Toast t = Toast.makeText(getBaseContext(), "This function requires Android 10 or higher !", Toast.LENGTH_LONG);
                        t.show();
                    }
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("darkSys", false);
                }
                editor.commit();
            }
        });

        alarmUtils = alarmManagerUtils.getInstance(this);
        spinner_ddl = (Spinner)findViewById(R.id.spinner2);

        notiSwitch = findViewById(R.id.setting_noti);
        if (setting.getBoolean("notification", false)) {
            notiSwitch.setChecked(true);
            spinner_ddl.setEnabled(true);
            timeText.setClickable(true);
        } else {
            spinner_ddl.setEnabled(false);
            timeText.setClickable(false);
            notiSwitch.setChecked(false);
        }
        notiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("notification", true);
                    spinner_ddl.setEnabled(true);
                    timeText.setClickable(true);
                    alarmUtils.cancel();
                    alarmUtils.createGetUpAlarmManager();
                    alarmUtils.getUpAlarmManagerStartWork(data_hour, data_min);
                    Toast t = Toast.makeText(getBaseContext(), "The notification is now enabled on "+data_hour+":"+data_min+", remember to check the system battery setting !", Toast.LENGTH_LONG);
                    t.show();
                } else {
                    spinner_ddl.setEnabled(false);
                    timeText.setClickable(false);
                    alarmUtils.cancel();
                    editor.putBoolean("notification", false);
                }
                editor.commit();
            }
        });

        double b = 2;
        double i = Integer.parseInt(setting.getString("ddlDays", "1"))/b-0.5;
        spinner_ddl.setSelection((int)i, false);
        spinner_ddl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] ddlRmd = getResources().getStringArray(R.array.ddlRmd);
                ddlDays = ddlRmd[position];
                editor.putString("ddlDays", ddlDays);
                if (spinner_ddl.isEnabled()) {
                    Toast t = Toast.makeText(getBaseContext(), "The notification will remind you "+ddlDays+" days before the DDL !", Toast.LENGTH_LONG);
                    t.show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    public void timeDialog(View view) {
        c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        new TimePickerDialog(SettingsActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                c.setTimeInMillis(System.currentTimeMillis());
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                data_hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
                data_min = String.valueOf(c.get(Calendar.MINUTE));
                SharedPreferences setting = getSharedPreferences("Setting", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = setting.edit();
                editor.putString("hour", pad(data_hour));
                editor.putString("min", pad(data_min));
                timeText.setText(pad(data_hour) + ":" + pad(data_min));
                editor.commit();
                alarmUtils.cancel();
                alarmUtils.createGetUpAlarmManager();
                alarmUtils.getUpAlarmManagerStartWork(data_hour, data_min);
                Toast t = Toast.makeText(getBaseContext(), "The notification is now enabled on "+data_hour+":"+data_min+", remember to check the system battery setting !", Toast.LENGTH_LONG);
                t.show();
            }
        }, hour, minute, true).show();
    }

    public String pad(String input) {
        String str = "";
        if (Integer.parseInt(input) >= 10) {
            str = Integer.toString(Integer.parseInt(input));
        } else {
            str = "0" + input;
        }
        return str;
    }
}