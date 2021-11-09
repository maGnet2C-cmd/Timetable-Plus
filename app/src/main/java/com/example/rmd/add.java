package com.example.rmd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.rmd.ui.deadlines.DeadlinesFragment;

import java.util.Calendar;
import java.util.regex.Pattern;

public class add extends Activity {

    private Calendar c;
    private TextView dateDialog, dateText;
    private int year, month, day;
    private String selectedDate=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getDate();
        dateDialog = findViewById(R.id.dd_dateDialog);
        dateText = findViewById(R.id.dd_dateText);
        setResult(1);
    }

    //pop up the DatePickerDialog
    public void DateDialog(View view) {
        switch (view.getId()) {
            case R.id.dd_dateText:
                DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        selectedDate = year+"-"+(++month)+"-"+day;
                        dateText.setText(selectedDate);
                    }
                };
                DatePickerDialog dialog=new DatePickerDialog(add.this, 0,listener,year,month,day);
                dialog.show();
                break;
            default:
                break;
        }
    }

    //assign the cancel button
    public void cancel(View view) {
        finish();
    }

    //assign the save button
    public void save(View view) {
        EditText editText1=(EditText)findViewById(R.id.editText2);
        String code=editText1.getText().toString();
        EditText editText2=(EditText)findViewById(R.id.editText3);
        String comment=editText2.getText().toString();
        if (code.equals("") || comment.equals("") || selectedDate.equals("")) {
            Toast t = Toast.makeText(getBaseContext(), "Incomplete input !", Toast.LENGTH_LONG);
            t.show();
        } else if (code.contains("_") || comment.contains("_")) {
            Toast t = Toast.makeText(getBaseContext(), "Do not input with '_' !", Toast.LENGTH_LONG);
            t.show();
        }
        else {
                SharedPreferences sp = getSharedPreferences("activity", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                String cd_key = sp.getString("key", "");
                String data = code+"_"+comment+"_"+selectedDate;
                editor.putString(cd_key, data);
                String[] num = cd_key.split("cd_");
                cd_key = getCode(cd_key, num[1]);
                editor.putString("key", cd_key);
                editor.commit();
                finish();
        }
    }

    //generate the next key which is used to store data in SharedPreferences
    public static String getCode(String old, String num) {
        int n = num.length();
        int nums = Integer.parseInt(num) + 1;
        String newNum = String.valueOf(nums);
        n = Math.min(n, newNum.length());
        return old.subSequence(0, old.length()-n) + newNum;
    }

    //get current date
    private void getDate() {
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
    }
}