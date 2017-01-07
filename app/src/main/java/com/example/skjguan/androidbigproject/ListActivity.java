package com.example.skjguan.androidbigproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class ListActivity extends AppCompatActivity {

    private static final int CREATE_TIME = 1;
    private static final int IMPORTANT_LEVEL = 2;
    private static final int REMAIN_TIME = 3;
    private static final boolean AESCENDING = true;
    private static final boolean DESAESCENDING = false;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private AppCompatButton createTimeButton;
    private AppCompatButton remainTimeButton;
    private AppCompatButton importantLevelButton;

    private boolean isCreateTimeButtonChecked;
    private boolean isImportantLevelButtonChecked;
    private boolean isRemainTimeButtonChecked;

    private  myDB db;
    private TodoItemAdapter adapter;
    private ArrayList<TodoItem> todolist;



//todo sync

    private void initlist() {
        db =  new myDB(ListActivity.this);
        boolean notLast = false;
        db.deleteByTitle("test");
        db.insert("test", "hehe", "2015.12.01", "2016.01.01", 1 ,"gray");
        db.insert("test", "hehe", "2016.09.01", "2017.12.02", 2 ,"red");
        db.insert("test", "hehe", "2017.01.01", "2017.11.01", 3 ,"green");
        db.insert("test", "hehe", "2017.01.02", "2018.01.01", 4 ,"green");
        Cursor cursor = db.getTable();
        if (cursor != null && cursor.getCount() > 0) {
            notLast = true;
            cursor.moveToFirst();
        }
        while (notLast) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String deadline = cursor.getString(cursor.getColumnIndex("deadline"));
            String createTime = cursor.getString(cursor.getColumnIndex("createTime"));
            int remindingTime = cursor.getInt(cursor.getColumnIndex("remindingTime"));
            String  importanceLevel = cursor.getString(cursor.getColumnIndex("importanceLevel"));
            TodoItem item = new TodoItem(title, content, createTime, deadline, remindingTime, importanceLevel);
            todolist.add(item);
//            item.print();
            notLast = cursor.moveToNext();
        }
        adapter = new TodoItemAdapter(ListActivity.this, todolist);
        recyclerView.setAdapter(adapter);
        db.close();

    }

    public void changeSort(int type, boolean isAscending) {


        if (!todolist.isEmpty()) {
            switch (type) {
                case CREATE_TIME: {
                    CreateTimeComparator comparator = new CreateTimeComparator(isAscending);
                    Collections.sort(todolist, comparator);
                    break;
                }
                case IMPORTANT_LEVEL: {
                    ImportantLevelComparator importantLevelComparator = new ImportantLevelComparator(isAscending);
                    Collections.sort(todolist, importantLevelComparator);
                    break;
                }
                case REMAIN_TIME: {
                    RemainTimeComparator comparator = new RemainTimeComparator(isAscending);
                    Collections.sort(todolist, comparator);
                    break;

                }
                default:{
                    break;
                }


            }
            adapter.notifyDataSetChanged();

        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initlist();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        createTimeButton = (AppCompatButton) findViewById(R.id.createTimeButton);
        importantLevelButton = (AppCompatButton) findViewById(R.id.importanceLevelButton);
        remainTimeButton = (AppCompatButton) findViewById(R.id.remainTimeButton);

        isCreateTimeButtonChecked = false;
        isImportantLevelButtonChecked = false;
        isRemainTimeButtonChecked = false;

        todolist = new ArrayList<TodoItem>();
        initlist();
        changeSort(IMPORTANT_LEVEL, AESCENDING);
        changeSort(REMAIN_TIME, AESCENDING);
        createTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCreateTimeButtonChecked) {
                    changeSort(CREATE_TIME, DESAESCENDING);
                    isCreateTimeButtonChecked = false;
                }
                else {
                    changeSort(CREATE_TIME, AESCENDING);
                    isCreateTimeButtonChecked = true;
                }
            }
        });

        importantLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isImportantLevelButtonChecked) {
                    changeSort(IMPORTANT_LEVEL, DESAESCENDING);
                    isImportantLevelButtonChecked = false;
                }
                else {
                    changeSort(IMPORTANT_LEVEL, AESCENDING);
                    isImportantLevelButtonChecked = true;
                }
            }
        });

        remainTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRemainTimeButtonChecked) {
                    changeSort(REMAIN_TIME, DESAESCENDING);
                    isRemainTimeButtonChecked = false;
                }
                else {
                    changeSort(REMAIN_TIME, AESCENDING);
                    isRemainTimeButtonChecked = true;
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, CreateItemActivity.class);
                intent.putExtra("requestCode", 2);
                startActivityForResult(intent, 2);
            }
        });

    }
}
