package com.example.skjguan.androidbigproject;
import android.util.Log;

import java.util.Comparator;


/**
 * Created by jeyo on 2017/1/7.
 */

public class ImportantLevelComparator implements Comparator {

    boolean isAscending = false;

    public ImportantLevelComparator(boolean isAscending) {
        this.isAscending = isAscending;
    }

    @Override
    public int compare(Object o1, Object o2) {
        TodoItem item1 = (TodoItem) o1;
        TodoItem item2 = (TodoItem) o2;
//        Log.e("item1", item1.getImportanceLevel());
//        Log.e("item2", item2.getImportanceLevel());
//        Log.e("res", String.valueOf(item1.getImportanceLevel().compareTo(item2.getImportanceLevel())));

        int res = item1.getImportanceLevel().compareTo(item2.getImportanceLevel());

        return isAscending ? res : -res;
    }
}
