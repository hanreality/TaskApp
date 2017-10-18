package com.punuo.sys.app.taskapp.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.punuo.sys.app.taskapp.R;
import com.punuo.sys.app.taskapp.adapter.TaskAdapter;
import com.punuo.sys.app.taskapp.adapter.UnfinishedAdapter;
import com.punuo.sys.app.taskapp.db.SqliteManager;
import com.punuo.sys.app.taskapp.model.ActionInfo;
import com.punuo.sys.app.taskapp.model.Task;
import com.punuo.sys.app.xungeng.model.TaskInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.punuo.sys.app.taskapp.model.ActionInfo.taskInfoInterface;

public class Wrecker extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.new_task)
    TextView newTask;
    @Bind(R.id.b1)
    View b1;
    @Bind(R.id.unread_num)
    TextView unreadNum;
    @Bind(R.id.unfinished)
    TextView unfinished;
    @Bind(R.id.b2)
    View b2;
    @Bind(R.id.finished)
    TextView finished;
    @Bind(R.id.b3)
    View b3;
    @Bind(R.id.lllayout)
    LinearLayout lllayout;
    @Bind(R.id.llayout)
    LinearLayout llayout;
    @Bind(R.id.new_task_list)
    ListView newTaskList;
    @Bind(R.id.unfinished_list)
    ListView unfinishedList;
    @Bind(R.id.finished_list)
    ListView finishedList;
    private List<TaskInfo> newTasklist;
    private SqliteManager db = new SqliteManager();
    private TaskAdapter newTaskAdapter;
    private UnfinishedAdapter unfinishedAdapter;
    private TaskAdapter finishedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrecker);
        ButterKnife.bind(this);
        newTask.setOnClickListener(this);
        unfinished.setOnClickListener(this);
        finished.setOnClickListener(this);
        init();
        newTaskAdapter = new TaskAdapter(this, Task.newTasks);
        newTaskList.setAdapter(newTaskAdapter);
        newTaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                db.setStateToOne(Task.newTasks.get(position).getId(), Wrecker.this);
                Intent intent = new Intent(Wrecker.this, Execute.class);
                intent.putExtra("type",1);
                intent.putExtra("task", Task.newTasks.get(position));
                try {
                    taskInfoInterface.sendTaskCheck(Task.newTasks.get(position).getTask_id());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                startActivity(intent);

            }
        });
        unfinishedAdapter = new UnfinishedAdapter(this, Task.unfinishedTasks);
        unfinishedList.setAdapter(unfinishedAdapter);
        unfinishedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Wrecker.this, Execute.class);
                intent.putExtra("type",1);
                intent.putExtra("task", Task.unfinishedTasks.get(position));
                startActivity(intent);
            }
        });
        finishedAdapter = new TaskAdapter(this, Task.finishedTasks);
        finishedList.setAdapter(finishedAdapter);
        finishedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Wrecker.this, Execute.class);
                intent.putExtra("type",2);
                intent.putExtra("task", Task.finishedTasks.get(position));
                startActivity(intent);

            }
        });
        ActionInfo.handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                loadTask();
                onResume();
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        Task.newTasks.clear();
        Task.newTasks.addAll(db.getInfo(Wrecker.this, 0));
        Task.unfinishedTasks.clear();
        Task.unfinishedTasks.addAll(db.getInfo(Wrecker.this, 1));
        Task.finishedTasks.clear();
        Task.finishedTasks.addAll(db.getInfo(Wrecker.this, 2));
        int size = Task.newTasks.size();
        if (size > 0) {
            unreadNum.setText(String.valueOf(size));
            unreadNum.setVisibility(View.VISIBLE);
        } else {
            unreadNum.setVisibility(View.INVISIBLE);
        }
        newTaskAdapter.notifyDataSetChanged();
        unfinishedAdapter.notifyDataSetChanged();
        finishedAdapter.notifyDataSetChanged();
    }

    public void loadTask() {
        try {
            newTasklist = taskInfoInterface.getTaskInfo();
            Log.d("111",""+newTasklist.size());
            ActionInfo.devId=taskInfoInterface.getDevId();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        for (TaskInfo newTask : newTasklist) {
            SqliteManager sq = new SqliteManager();
            sq.insertInfo(Wrecker.this, newTask);
        }
    }

    private void init() {
        b1.setVisibility(View.VISIBLE);
        b2.setVisibility(View.INVISIBLE);
        b3.setVisibility(View.INVISIBLE);
        loadTask();
        Task.newTasks = db.getInfo(Wrecker.this, 0);
        Task.unfinishedTasks = db.getInfo(Wrecker.this, 1);
        Task.finishedTasks = db.getInfo(Wrecker.this, 2);
        int size = Task.newTasks.size();
        if (size > 0) {
            unreadNum.setText(String.valueOf(size));
            unreadNum.setVisibility(View.VISIBLE);
        } else {
            unreadNum.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActionInfo.handler = null;//销毁时候清除
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_task:
                newTask.setTextColor(0xff228B22);
                unfinished.setTextColor(Color.BLACK);
                finished.setTextColor(Color.BLACK);
                b1.setVisibility(View.VISIBLE);
                b2.setVisibility(View.INVISIBLE);
                b3.setVisibility(View.INVISIBLE);
                newTaskList.setVisibility(View.VISIBLE);
                unfinishedList.setVisibility(View.GONE);
                finishedList.setVisibility(View.GONE);
                break;
            case R.id.unfinished:
                newTask.setTextColor(Color.BLACK);
                unfinished.setTextColor(0xff228B22);
                finished.setTextColor(Color.BLACK);
                b1.setVisibility(View.INVISIBLE);
                b2.setVisibility(View.VISIBLE);
                b3.setVisibility(View.INVISIBLE);
                newTaskList.setVisibility(View.GONE);
                unfinishedList.setVisibility(View.VISIBLE);
                finishedList.setVisibility(View.GONE);
                break;
            case R.id.finished:
                newTask.setTextColor(Color.BLACK);
                unfinished.setTextColor(Color.BLACK);
                finished.setTextColor(0xff228B22);
                b1.setVisibility(View.INVISIBLE);
                b2.setVisibility(View.INVISIBLE);
                b3.setVisibility(View.VISIBLE);
                newTaskList.setVisibility(View.GONE);
                unfinishedList.setVisibility(View.GONE);
                finishedList.setVisibility(View.VISIBLE);
                break;
        }
    }
}
