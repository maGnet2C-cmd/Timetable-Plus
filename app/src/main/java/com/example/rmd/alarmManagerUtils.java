package com.example.rmd;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

public class alarmManagerUtils {
    private static final long TIME_INTERVAL = 1 * 1000;
    private Context context;
    public static AlarmManager am;
    public static PendingIntent pendingIntent;
    private Calendar calendar;
    private static alarmManagerUtils instance = null;

    private alarmManagerUtils(Context aContext) {
        this.context = aContext;
    }

    public static alarmManagerUtils getInstance(Context aContext) {
        if (instance == null) {
            synchronized (alarmManagerUtils.class) {
                if (instance == null) {
                    instance = new alarmManagerUtils(aContext);
                }
            }
        }
        return instance;
    }

    public void createGetUpAlarmManager() {
        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, myService.class);
        pendingIntent = PendingIntent.getService(context, 0, intent, 0);
    }

    @SuppressLint("NewApi")
    public void cancel() {
        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, myService.class);
        pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        am.cancel(pendingIntent);
    }

    @SuppressLint("NewApi")
    public void getUpAlarmManagerStartWork(String hour, String min) {
        System.out.println(hour+"!!"+min);
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hour));
        calendar.set(Calendar.MINUTE,Integer.parseInt(min));
        calendar.set(Calendar.SECOND,00);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // >6.0
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), pendingIntent);
        } else {
            am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    pendingIntent);
        }
    }

    @SuppressLint("NewApi")
    public void getUpAlarmManagerWorkOnOthers() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// >6.0
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + AlarmManager.INTERVAL_DAY, pendingIntent);
        } else {
            am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                    + AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

}
