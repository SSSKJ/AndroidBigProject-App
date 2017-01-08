package com.example.skjguan.androidbigproject;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        final ViewHolder holder = new ViewHolder(view);
        holder.title = (TextView)view.findViewById(R.id.title);
        holder.content = (TextView)view.findViewById(R.id.content);
        holder.createTime = (TextView)view.findViewById(R.id.createTime);
        holder.deadline = (TextView)view.findViewById(R.id.deadline);
        holder.remindingTime = (TextView)view.findViewById(R.id.remindingTime);
        holder.importanceLevel = (TextView)view.findViewById(R.id.importanceLevel);
        holder.isLike = (ImageView) view.findViewById(R.id.isLike);
        view.setOnClickListener(new View.OnClickListener() {
            boolean isTap = false;
            @Override
            public void onClick(View v) {
                long now = System.currentTimeMillis();
                if (now - holder.mLastClickTime < holder.CLICK_TIME_INTERVAL) {
                    if (!isTap) {
                        holder.isLike.setImageResource(R.mipmap.heart);
                        isTap = true;
                    }
                    else {
                        holder.isLike.setImageResource(0);
                        isTap = false;
                    }
                    
                }
                holder.mLastClickTime = now;
            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.title.setText(todo_list.get(position).getTitle());
        holder.content.setText(todo_list.get(position).getContent());
        holder.createTime.setText(todo_list.get(position).getCreateTime());
        holder.deadline.setText(todo_list.get(position).getDeadline());
        holder.remindingTime.setText(todo_list.get(position).getRemindingTime());
        String color = todo_list.get(position).getImportanceLevel();
        holder.importanceLevel.setText(color);
        switch (color) {
            case "gray": {
                holder.isLike.setBackgroundColor(Color.parseColor("#EEEEEE"));
                break;
            }
            case "green": {
                holder.isLike.setBackgroundColor(Color.parseColor("#7bd67b"));
                break;
            }
            case "red": {
                holder.isLike.setBackgroundColor(Color.parseColor("#f16c6d"));
                break;
            }
            default: {
                break;
            }
        }


    }

    @Override
    public int getItemCount() {
        return todo_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        public ViewHolder(View itemView) {
            super(itemView);
        }
        private static long mLastClickTime = System.currentTimeMillis();
        private static final long CLICK_TIME_INTERVAL = 300;

        TextView title;
        TextView content;
        TextView createTime;
        TextView deadline;
        TextView remindingTime;
        TextView importanceLevel;
        ImageView isLike;

        // onClick Listener for view
        @Override
        public void onClick(View v) {

            if (v.getId() == isLike.getId()){
                isLike.setImageResource(R.mipmap.heart);
            }
        }
    }
}
