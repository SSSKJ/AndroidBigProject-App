package com.example.skjguan.androidbigproject;

import android.util.Log;

/**
 * Created by jeyo on 2017/1/7.
 */

public class TodoItem {
    private String title;
    private String content;
    private String createTime;
    private String deadline;
    private int remindingTime;
    private String importanceLevel;

    public TodoItem( String title, String content, String createTime, String deadline, int remindingTime, String importanceLevel) {
        this.content = content;
        this.title = title;
        this.createTime = createTime;
        this.deadline = deadline;
        this.remindingTime = remindingTime;
        this.importanceLevel = importanceLevel;
    }

    public void print() {
        Log.e("title", title);
        Log.e("content", content);
        Log.e("create", createTime);
        Log.e("deadline", deadline);
        Log.e("remindingTime", String.valueOf(remindingTime));
        Log.e("importanceLevel", importanceLevel);

    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getRemindingTime() {
        return String.valueOf(remindingTime);
    }

    public String getImportanceLevel() {
        return importanceLevel;
    }
}
