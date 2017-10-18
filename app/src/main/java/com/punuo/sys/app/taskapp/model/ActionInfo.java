package com.punuo.sys.app.taskapp.model;

import android.os.Handler;

import com.punuo.sys.app.xungeng.TaskInfoInterface;


/**
 * Created by acer on 2016/11/27.
 */

public class ActionInfo {
    public static final String TASK_READ_ACTION = "com.punuo.sys.app.task_read";
    public static final String TASK_MAINTEVENT_ACTION = "com.punuo.sys.app.MaintEvent";
    public static final String TASK_RECEIVE_ACTION="com.punuo.sys.app.task_receive";
    /**aidl接口*/
    public static TaskInfoInterface taskInfoInterface;
    public static Handler handler;
    public static String devId;
}
