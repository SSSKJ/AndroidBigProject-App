package com.example.skjguan.androidbigproject;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by jeyo on 2017/1/7.
 */

public class RemainTimeComparator implements Comparator {

    boolean isAscending = false;

    public RemainTimeComparator(boolean isAscending) {
        this.isAscending = isAscending;
    }

    @Override
    public int compare(Object o1, Object o2) {
        TodoItem item1 = (TodoItem) o1;
        TodoItem item2 = (TodoItem) o2;
        Calendar beginDay = Calendar.getInstance();
        Calendar deadline = Calendar.getInstance();
        DateFormat inputFormat = new SimpleDateFormat("yyyy.MM.dd");

        try {
            Date date1 = inputFormat.parse(item1.getCreateTime());
            Date ddlDate1 = inputFormat.parse(item1.getDeadline());
            beginDay.setTime(date1);
            deadline.setTime(ddlDate1);
            long diff =  deadline.getTimeInMillis() - beginDay.getTimeInMillis() ; //result in millis
            long remainDays1 = diff / (24 * 60 * 60 * 1000);

            Date date2 = inputFormat.parse(item2.getCreateTime());
            Date ddlDate2 = inputFormat.parse(item2.getDeadline());
            beginDay.setTime(date2);
            deadline.setTime(ddlDate2);
            diff =  deadline.getTimeInMillis() - beginDay.getTimeInMillis() ; //result in millis
            long remainDays2 = diff / (24 * 60 * 60 * 1000);

//            Log.e("R1", String.valueOf(remainDays1));
//            Log.e("R2", String.valueOf(remainDays2));


            if (remainDays1 > remainDays2) {
                return isAscending ? 1 : -1;
            }
            else if (remainDays1 < remainDays2) {
                return isAscending ? -1 : 1;
            }
            else
                return 0;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
