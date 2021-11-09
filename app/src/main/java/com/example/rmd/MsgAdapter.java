package com.example.rmd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rmd.ui.deadlines.DeadlinesFragment;
import com.example.rmd.ui.list.ListFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MsgAdapter extends BaseAdapter {
    private List<Msg> mMsgList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private String type;

    public MsgAdapter(List<Msg> msgList, Context context, String requiredType) {
        this.mMsgList = msgList;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.type = requiredType;
    }

    @Override
    public int getCount() {
        return mMsgList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMsgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //assign data to views
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            if (type == "ddl") {
                convertView = mLayoutInflater.inflate(R.layout.cardview_ddl,parent,false);
                viewHolder = new ViewHolder();
                viewHolder.date = convertView.findViewById(R.id.card_date);
                viewHolder.delete = convertView.findViewById(R.id.card_delete);
            } else if (type == "timetable") {
                convertView = mLayoutInflater.inflate(R.layout.cardview_timetable,parent,false);
                viewHolder = new ViewHolder();
                viewHolder.date = convertView.findViewById(R.id.card_date);
                viewHolder.delete = convertView.findViewById(R.id.card_delete);
            }
            viewHolder.imageView = convertView.findViewById(R.id.imageview1);
            viewHolder.titleTV = convertView.findViewById(R.id.title_tv);
            viewHolder.contentTV = convertView.findViewById(R.id.content_tv);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Msg msg = mMsgList.get(position);
        if (type == "history") {
            viewHolder.imageView.setImageResource(msg.getImageResourceID());
        } else {
            viewHolder.date.setText(msg.getDate());
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteThis_dialog(msg.getKey(), type);
                }
            });
        }
        viewHolder.titleTV.setText(msg.getTitle());
        viewHolder.contentTV.setText(msg.getContent());
        return convertView;
    }

    private static class ViewHolder{
        ImageView imageView;
        TextView titleTV;
        TextView contentTV;
        TextView date;
        TextView delete;
    }

    //pop up the confirmation dialog
    public void deleteThis_dialog(final String key, final String type) {
        Context context;
        if (type.equals("ddl")) {
            context = DeadlinesFragment.mContext;
        } else {
            context = ListFragment.mContext;
        }
        AlertDialog.Builder cf_dialog = new AlertDialog.Builder(context);

        cf_dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (type.equals("ddl")) {
                    DeadlinesFragment deadlinesFragment = new DeadlinesFragment();
                    deadlinesFragment.clearThis_data(key);
                } else {
                    ListFragment listFragment = new ListFragment();
                    listFragment.clearThis_data(key);
                }
            }
        });
        cf_dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        cf_dialog.setMessage("Delete this activity?");
        cf_dialog.setTitle("Warning");
        cf_dialog.show();
    }
}
