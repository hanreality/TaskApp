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

public class TaskAdapter extends BaseAdapter {
    private Context context;
    private List<TaskInfo> taskInfoList = new ArrayList<TaskInfo>();

    public TaskAdapter(Context context, List<TaskInfo> taskInfoList) {
        this.context = context;
        this.taskInfoList = taskInfoList;

    }

    @Override
    public int getCount() {
        return taskInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return taskInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        TaskInfo taskInfo = taskInfoList.get(position);
        Holder holder = null;
        if (view == null) {
            holder = new Holder();
            view = LayoutInflater.from(context).inflate(R.layout.newtask, parent, false);
            holder.pic = (ImageView) view.findViewById(R.id.l1);
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.time = (TextView) view.findViewById(R.id.time);
            holder.lastmsg = (TextView) view.findViewById(R.id.lastmsg);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        holder.pic.setImageResource(Integer.parseInt(R.drawable.voip_camerachat + ""));
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
