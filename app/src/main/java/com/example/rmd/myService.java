package com.example.rmd;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class myService extends Service {
    public myService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }

    private alarmManagerUtils alarmUtils;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                SharedPreferences sp = getSharedPreferences("activity", Activity.MODE_PRIVATE);
                SharedPreferences setting = getSharedPreferences("setting", Activity.MODE_PRIVATE);
                String cd_key = "cd_00001";
                String cd_test = cd_key;
                int i = 0;
                if (!sp.contains("cd_00001") && sp.contains("cd_00002")) {
                    cd_test = "cd_00002";
                    i = 1;
                }
                while (sp.contains(cd_test)) {
                    String[] num = cd_test.split("cd_");
                    cd_test = add.getCode(cd_test, num[1]);
                    if (sp.contains(add.getCode(cd_test, cd_test.split("cd_")[1]))) {
                        i = i+2;
                        cd_test = add.getCode(cd_test, cd_test.split("cd_")[1]);
                    } else {i++;}
                }
                int DDLdays = Integer.parseInt(setting.getString("ddlDays", "1"));
                int DDLcount=0;
                while (i>0) {
                    if (sp.contains(cd_key)) {
                        String data = sp.getString(cd_key, null);
                        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                        Date current_date = new Date(System.currentTimeMillis());
                        try {
                            Date target_date = sf.parse(data.split("_")[2]);
                            long current = current_date.getTime();
                            long target = target_date.getTime();
                            if (((target-current)/(1000*60*60*24)+1) == DDLdays) {
                                DDLcount++;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    String[] num = cd_key.split("cd_");
                    cd_key = add.getCode(cd_key, num[1]);
                    i--;
                }

                if (DDLcount!=0) {
                    alarmUtils = alarmManagerUtils.getInstance(bottomNavigation.mConText);
                    NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                    NotificationChannel notificationChannel = new NotificationChannel("ddl", "deadline", NotificationManager.IMPORTANCE_HIGH);
                    manager.createNotificationChannel(notificationChannel);
                    Notification notification = new NotificationCompat.Builder(bottomNavigation.mConText, "ddl")
                            .setSmallIcon(R.drawable.notification_icon)
                            .setContentTitle("Timetable+ Reminder")
                            .setContentText("You have "+DDLcount+" DDL due in "+DDLdays+" days later !")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .build();
                    manager.notify(1,notification);
                }


            }
        }).start();
        alarmManagerUtils.getInstance(getApplicationContext()).getUpAlarmManagerWorkOnOthers();
        return super.onStartCommand(intent, flags, startId);
    }
}
