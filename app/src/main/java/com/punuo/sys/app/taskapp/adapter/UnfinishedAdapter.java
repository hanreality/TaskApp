package com.punuo.sys.app.taskapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.punuo.sys.app.taskapp.R;
import com.punuo.sys.app.xungeng.model.TaskInfo;

import java.util.ArrayList;
import java.util.List;


public class UnfinishedAdapter extends BaseAdapter {
    private Context context;
    private List<TaskInfo> list = new ArrayList<TaskInfo>();

    public UnfinishedAdapter(Context context, List<TaskInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        TaskInfo taskInfo = list.get(position);
        Holder holder = null;
        if (view == null) {
            holder = new Holder();
            view = LayoutInflater.from(context).inflate(R.layout.unfinished, parent, false);
            holder.pic = (ImageView) view.findViewById(R.id.wwcp);
            holder.name = (TextView) view.findViewById(R.id.wname);
            holder.time = (TextView) view.findViewById(R.id.wtime);
            holder.lastmsg = (TextView) view.findViewById(R.id.wdetail);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        holder.pic.setImageResource(Integer.parseInt(R.drawable.find_more_friend_photograph_icon + ""));
        holder.name.setText(taskInfo.getTask_id());
        holder.time.setText(taskInfo.getReceive_time());
        holder.lastmsg.setText(taskInfo.getLocation());

        return view;
    }

    class Holder {
        ImageView pic;
        TextView name;
        TextView time;
        TextView lastmsg;
    }
}
