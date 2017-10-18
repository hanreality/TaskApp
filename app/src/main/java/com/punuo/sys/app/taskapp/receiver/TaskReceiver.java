package com.punuo.sys.app.taskapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.punuo.sys.app.taskapp.model.ActionInfo;
import com.punuo.sys.app.xungeng.model.TaskInfo;

import java.util.List;

public class TaskReceiver extends BroadcastReceiver {
    private List<TaskInfo> newTasklist;

    public TaskReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        //收到工单
        if (intent.getAction().equals(ActionInfo.TASK_RECEIVE_ACTION)) {
            if (ActionInfo.handler!=null)
            ActionInfo.handler.sendEmptyMessage(0x111);
        }
    }
}
