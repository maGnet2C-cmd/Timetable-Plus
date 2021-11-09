package com.example.rmd;

import android.content.SharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MsgUtil {

    //store the deadlines data in ArrayList
    public static List<Msg> DDLgetMsgList(SharedPreferences sp, int i){
        List<Msg> DDLmsgList = new ArrayList<>();
        String cd_key = "cd_00001";
        String title, content, date;
        int id = 1;

        while (i>0) {
            if (sp.contains(cd_key)) {
                String data = sp.getString(cd_key, null);
                title = data.split("_")[0];
                content = data.split("_")[1];
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                Date current_date = new Date(System.currentTimeMillis());
                try {
                    Date target_date = sf.parse(data.split("_")[2]);
                    long current = current_date.getTime();
                    long target = target_date.getTime();
                    int days = (int)((target-current) / (1000*60*60*24));
                    if (days >= 0) {
                        date = String.valueOf(days+1)+" days left";
                    } else {
                        date = String.valueOf(1-days)+" days past";
                    }
                    Msg msg = new Msg(id, R.drawable.bg1, title, content, date, cd_key);
                    DDLmsgList.add(msg);
                    id++;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            String[] num = cd_key.split("cd_");
            cd_key = add.getCode(cd_key, num[1]);
            i--;
        }
        return DDLmsgList;
    }

    //store the timetable data in ArrayList
    public static List<Msg> TTgetMsgList(SharedPreferences tt, int i) {
        List<Msg> TTmsgList = new ArrayList<>();
        String cd_key = "tt_00001";
        String title, content, date;
        int id = 1;
        while (i>0) {
            if(tt.contains(cd_key)) {
                String data = tt.getString(cd_key, null);
                title = data.split("_")[4];
                content = "Building: " + data.split("_")[0] + "    Room: " + data.split("_")[5];
                date = data.split("_")[1] + "   " + data.split("_")[2]+":"+data.split("_")[3];
                Msg msg = new Msg(id, R.drawable.bg1, title, content, date, cd_key);
                TTmsgList.add(msg);
                id++;
            }
            String[] num = cd_key.split("tt_");
            cd_key = add.getCode(cd_key, num[1]);
            i--;
        }
        return TTmsgList;
    }
}
