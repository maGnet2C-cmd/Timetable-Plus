

package com.example.rmd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class add_timetable extends Activity {
    private String data_building;
    private String data_week;
    private String data_hour = "100";
    private String data_min = "100";
    private Calendar c;
    private TextView timeDialog, timeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timetable);
        timeText = findViewById(R.id.tt_textView5);
        //assign listeners for these Spinners
        Spinner spinner_b = (Spinner)findViewById(R.id.tt_spinner1);
        Spinner spinner_w = (Spinner)findViewById(R.id.tt_spinner2);
        spinner_b.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] buildings = getResources().getStringArray(R.array.building);
                data_building = buildings[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        spinner_w.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] week = getResources().getStringArray(R.array.week);
                data_week = week[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        setResult(2);
    }

    //assign listener for the save button
    public void save(View view) {
        EditText editText1=(EditText)findViewById(R.id.tt_editText1);
        String tt_code=editText1.getText().toString();
        EditText editText2=(EditText)findViewById(R.id.tt_editText);
        String tt_room=editText2.getText().toString();
        if (data_hour.equals("100") || data_min.equals("100") || tt_code.equals("") || tt_room.equals("")) {
            Toast.makeText(getBaseContext(), "Incomplete input !", Toast.LENGTH_LONG).show();
        } else if(tt_code.contains("_") || tt_room.contains("_")) {
            Toast t = Toast.makeText(getBaseContext(), "Do not input with '_' !", Toast.LENGTH_LONG);
            t.show();
        } else {
            SharedPreferences tt = getSharedPreferences("timetable", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor1 = tt.edit();
            String cd_key = tt.getString("key", "");
            String data = data_building+"_"+data_week+"_"+data_hour+"_"+data_min+"_"+tt_code+"_"+tt_room;
            editor1.putString(cd_key, data);
            String[] num = cd_key.split("tt_");
            cd_key = add.getCode(cd_key, num[1]);
            editor1.putString("key", cd_key);
            editor1.commit();
            finish();
        }
    }

    //assign listener for the cancel button
    public void cancel(View view) {
        finish();
    }

    //pop up the TimePickerDialog
    public void timeDialog(View view) {
                c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                new TimePickerDialog(add_timetable.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        c.setTimeInMillis(System.currentTimeMillis());
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);
                        data_hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
                        data_min = String.valueOf(c.get(Calendar.MINUTE));
                        Toast.makeText(add_timetable.this, data_hour + ":" + data_min, Toast.LENGTH_SHORT).show();
                        timeText.setText(data_hour+":"+data_min);
                    }
                }, hour, minute, true).show();
        }
    }
