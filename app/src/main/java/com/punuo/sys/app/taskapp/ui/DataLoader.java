package com.punuo.sys.app.taskapp.ui;

import android.content.Context;
import android.os.IBinder;

import com.punuo.sys.app.xungeng.TaskInfoInterface;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.punuo.sys.app.taskapp.model.ActionInfo.taskInfoInterface;

/**
 * Created by acer on 2016/12/21.
 */

public class DataLoader {
    //线程池,线程数量为3
    ExecutorService executorService= Executors.newFixedThreadPool(3);
    private Context context;
    public DataLoader(Context context) {
        this.context=context;
    }
    //服务器的连接
    public void startBinderPool(final int bindCode){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                BinderPool binderPool = BinderPool.getInstance(context);
                IBinder binder = binderPool.queryBinder(bindCode);
                taskInfoInterface= TaskInfoInterface.Stub.asInterface(binder);
            }
        });
    }
}
