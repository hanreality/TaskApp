package com.punuo.sys.app.taskapp.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import com.punuo.sys.app.taskapp.R;
import com.punuo.sys.app.taskapp.db.SqliteManager;
import com.punuo.sys.app.xungeng.model.TaskInfo;

import java.sql.Date;
import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.punuo.sys.app.taskapp.model.ActionInfo.taskInfoInterface;

/**
 * Created by acer on 2016/11/29.
 */
public class Execute extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.back)
    Button back;
    @Bind(R.id.menu)
    ImageButton menu;
    @Bind(R.id.execute)
    Button execute;
    @Bind(R.id.ignore)
    Button ignore;
    @Bind(R.id.receiver)
    TextView receiver;
    @Bind(R.id.task_type)
    TextView taskType;
    @Bind(R.id.location)
    TextView location;
    @Bind(R.id.task_id)
    TextView taskId;
    @Bind(R.id.vehicle_fault_type)
    TextView vehicleFaultType;
    @Bind(R.id.vehicle_fault_num)
    TextView vehicleFaultNum;
    @Bind(R.id.cargo)
    TextView cargo;
    @Bind(R.id.receive_time)
    TextView receiveTime;
    @Bind(R.id.chedao)
    TextView chedao;
    @Bind(R.id.location2)
    TextView location2;
    @Bind(R.id.event_time)
    TextView eventTime;
    @Bind(R.id.parking_position)
    TextView parkingPosition;
    @Bind(R.id.info_source)
    TextView infoSource;
    @Bind(R.id.task_name)
    TextView taskName;
    @Bind(R.id.order)
    TextView order;
    @Bind(R.id.wrecker_num)
    TextView wreckerNum;
    @Bind(R.id.wrecker_type)
    TextView wreckerType;
    @Bind(R.id.driver)
    TextView driver;
    @Bind(R.id.co_driver)
    TextView coDriver;
    /**
     *  @param task 工单数据对象
     * */
    private TaskInfo task;
    /**
     *  @param type 根据其值判断是否已经执行
     * */
    private int type;
    /**
     *  @param flag 根据其值判断进行到哪一步
     * */
    private int flag;

    TableLayout feelayout;
    EditText shishou;//实际收费
    EditText yingshou;//应该收费
    int pos;
    String degree_of_satisfaction;
    private SqliteManager sql=new SqliteManager();
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private SelectAddPopupWindow menuWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        execute.setOnClickListener(this);
        menu.setOnClickListener(this);
        ignore.setOnClickListener(this);
        task = getIntent().getParcelableExtra("task");
        type = getIntent().getIntExtra("type",0);
        receiver.setText(task.getReceiver());
        taskType.setText(task.getTask_type());
        location.setText(task.getLocation());
        taskId.setText(task.getTask_id());
        vehicleFaultType.setText(task.getVehicle_fault_type());
        vehicleFaultNum.setText(task.getVehicle_fault_num());
        cargo.setText(task.getCargo());
        receiveTime.setText(task.getReceive_time());
        location2.setText(task.getLocation());
        eventTime.setText(task.getEvent_time());
        parkingPosition.setText(task.getParking_position());
        infoSource.setText(task.getInfo_source());
        taskName.setText(task.getTask_name());
        order.setText(task.getOrder());
        wreckerNum.setText(task.getWrecker_num());
        wreckerType.setText(task.getWrecker_type());
        driver.setText(task.getDriver());
        coDriver.setText(task.getCo_driver());
        if (type == 2) {
            execute.setVisibility(View.GONE);
            ignore.setVisibility(View.GONE);
            menu.setEnabled(false);
            menu.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.ignore:
                finish();
                break;
            case R.id.menu:
                menuWindow = new SelectAddPopupWindow(Execute.this);
                menuWindow.showAsDropDown(menu);
                break;
            case R.id.execute:
                switch (flag){
                    case 0:
                        flag=1;
                        execute.setText("开始出车");
                        break;
                    case 1:
                        flag = 2;
                        execute.setText("到达现场");
                        //通知服务器工单出车时间
                        try {
                            taskInfoInterface.sendTaskReplyStart(task.getTask_id(), "start_time", format.format(new Date(System.currentTimeMillis())));
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        back.setVisibility(View.GONE);//禁止返回,工单执行已经开始
                        break;
                    case 2:
                        flag = 3;
                        execute.setText("撤离现场");
                        break;
                    case 3:
                        flag = 4;
                        execute.setText("解拖完成");
                        break;
                    case 4:
                        flag = 5;
                        execute.setText("收费");
                        break;
                    case 5:
                        feelayout = (TableLayout) getLayoutInflater().inflate(R.layout.fee_layout, null);
                        shishou = (EditText) feelayout.findViewById(R.id.shishou);
                        yingshou = (EditText) feelayout.findViewById(R.id.yingshou);
                        AlertDialog feedialog = new AlertDialog.Builder(this)
                                .setTitle("填写收费情况")
                                .setView(feelayout)
                                .setPositiveButton("上报", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String shishoufee = shishou.getText().toString().trim();
                                        String yingshoufee = yingshou.getText().toString().trim();
                                        //通知服务器收费信息
                                        try {
                                            taskInfoInterface.sendTaskReplyFee(task.getTask_id(),task.getWrecker_num(),yingshoufee,shishoufee);
                                        } catch (RemoteException e) {
                                            e.printStackTrace();
                                        }
                                        execute.setText("满意度调查");
                                        flag = 6;
                                    }
                                })
                                .setNegativeButton("忽略", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        execute.setText("满意度调查");
                                        flag = 6;
                                    }
                                })
                                .create();
                        feedialog.show();
                        feedialog.setCanceledOnTouchOutside(false);
                        break;
                    case 6:
                        final String[] items = new String[]{"满意", "一般", "不满意", "客户未评价"};
                        AlertDialog satisfactiondialog = new AlertDialog.Builder(this)
                                .setTitle("满意度调查")
                                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        pos = which;
                                    }
                                })
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        flag = 7;
                                        degree_of_satisfaction = items[pos];
                                        //通知服务器满意度
                                        try {
                                            taskInfoInterface.sendTaskReplySatisfaction(task.getTask_id(),degree_of_satisfaction);
                                        } catch (RemoteException e) {
                                            e.printStackTrace();
                                        }
                                        execute.performClick();
                                    }
                                }).create();
                        satisfactiondialog.show();
                        satisfactiondialog.setCanceledOnTouchOutside(false);
                        break;
                    case 7:
                        AlertDialog uploadphotos = new AlertDialog.Builder(this)
                                .setTitle("是否上传照片")
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Execute.this, AlbumAty.class);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        execute.setText("工单完成");
                                        ignore.setVisibility(View.GONE);
                                        flag = 10;
                                    }
                                })
                                .create();
                        uploadphotos.show();
                        uploadphotos.setCanceledOnTouchOutside(false);
                        break;
                    case 10:
                        sql.setStateToTwo(task.getId(), Execute.this);
                        //通知服务器工单完成
                        try {
                            taskInfoInterface.sendTaskComplete(task.getTask_id());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        flag=0;
                        finish();
                        break;

                }
                break;
        }
    }
}
