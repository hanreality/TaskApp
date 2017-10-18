package com.punuo.sys.app.taskapp.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.punuo.sys.app.xungeng.IBinderPool;

import java.util.concurrent.CountDownLatch;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Author chzjy
 * Date 2016/12/19.
 */

public class BinderPool {

    //上下文
    private Context mContext;
    //同步辅助类
    private CountDownLatch mCountDownLatch;
    //单例
    private static BinderPool mInstance;
    //获取AIDL代理对象的标识
    public static final int BINDER_TASK = 0;
    public static final int BINDER_NOTEBOOK = 1;

    private IBinderPool mBinderPool;

    private BinderPool(Context context) {
        //获取上下文
        mContext = context.getApplicationContext();
        //连接远程服务
        connectBinderPoolService();
    }

    /**
     * 单例模式
     *
     * @param context
     * @return
     */
    public static BinderPool getInstance(Context context) {
        if (mInstance == null) {
            synchronized (BinderPool.class) {
                if (mInstance == null) {
                    mInstance = new BinderPool(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 提供该类的一个查询方法
     *
     * @param binderCode
     * @return
     */
    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        try {
            if (mBinderPool != null) {
                binder = mBinderPool.queryBinder(binderCode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;
    }

    /**
     * 开启远程服务，并保持同步
     */
    private synchronized void connectBinderPoolService() {
        //同步辅助器，值为1
        mCountDownLatch = new CountDownLatch(1);
        //开启远程服务
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.punuo.sys.app.xungeng", "com.punuo.sys.app.xungeng.service.BinderPoolService"));
        intent.setAction("com.punuo.sys.app.task_receive");
        mContext.bindService(intent, conn, BIND_AUTO_CREATE);
        try {
            //同步辅助器
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接服务器接口
     */
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinderPool = IBinderPool.Stub.asInterface(service);
            //绑定死亡监听
            try {
                mBinderPool.asBinder().linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            //同步辅助器，值减1
            mCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    /**
     * 死亡监听接口，如果Binder对象在使用过程中突然停止服务，就会返回这个接口
     */
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            //取消死亡监听
            mBinderPool.asBinder().unlinkToDeath(mDeathRecipient, 0);
            //释放资源
            mBinderPool = null;
            //重新连接服务
            connectBinderPoolService();
        }
    };

}
