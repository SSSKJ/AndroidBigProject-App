package com.example.skjguan.androidbigproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jeyo on 2017/1/7.
 */

public class TodoItemAdapter extends RecyclerView.Adapter<TodoItemAdapter.ViewHolder> {

    private ArrayList<TodoItem> todo_list;
    private LayoutInflater mInflater;

    public TodoItemAdapter(Context context, ArrayList<TodoItem> todo_list) {
        this.todo_list = todo_list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.title = (TextView)view.findViewById(R.id.title);
        holder.content = (TextView)view.findViewById(R.id.content);
        holder.createTime = (TextView)view.findViewById(R.id.createTime);
        holder.deadline = (TextView)view.findViewById(R.id.deadline);
        holder.remindingTime = (TextView)view.findViewById(R.id.remindingTime);
        holder.importanceLevel = (TextView)view.findViewById(R.id.importanceLevel);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(todo_list.get(position).getTitle());
        holder.content.setText(todo_list.get(position).getContent());
        holder.createTime.setText(todo_list.get(position).getCreateTime());
        holder.deadline.setText(todo_list.get(position).getDeadline());
        holder.remindingTime.setText(todo_list.get(position).getRemindingTime());
        holder.importanceLevel.setText(todo_list.get(position).getImportanceLevel());
    }

    @Override
    public int getItemCount() {
        return todo_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        TextView title;
        TextView content;
        TextView createTime;
        TextView deadline;
        TextView remindingTime;
        TextView importanceLevel;
    }
}
