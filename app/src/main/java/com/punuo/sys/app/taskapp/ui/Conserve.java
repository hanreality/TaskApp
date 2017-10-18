package com.punuo.sys.app.taskapp.ui;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.punuo.sys.app.taskapp.R;
import com.punuo.sys.app.taskapp.model.ActionInfo;
import com.punuo.sys.app.xungeng.model.TaskInfo;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Conserve extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.back)
    Button back;
    @Bind(R.id.yanghuspinner)
    Spinner fangxiang_spinner;
    @Bind(R.id.chedaospinner)
    Spinner chedao_spinner;
    @Bind(R.id.bingduspinner)
    Spinner bingdu_spinner;
    @Bind(R.id.send)
    Button send;

    private static int Positionchoose;
    String direction;
    String road_condition;
    String lane;
    private static final String[] fangxiangchoose = {"沪宁方向", "宁沪方向"};
    private ArrayAdapter<String> fangxiangadapter;//适配器
    private static final String[] chedaochoose = {"路中间", "1车道", "2车道", "3车道", "4车道", "停车带", "路测",};//选择是否立即上传
    private ArrayAdapter<String> chedaoadapter;//适配器
    private static final String[] bingduchoose = {"坑槽松散", "沉陷翻浆", "路面污染", "护栏缺损", "路肩损坏", "其他"};//选择是否立即上传
    private ArrayAdapter<String> bingduadapter;//适配器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conserve);
        ButterKnife.bind(this);
        init();
        back.setOnClickListener(this);
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.send:
                TaskInfo.conserve_Task_Id=""+System.currentTimeMillis();
                try {
                    ActionInfo.taskInfoInterface.sendConserveTask(TaskInfo.conserve_Task_Id,TaskInfo.seq,direction,lane,road_condition);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                Toast.makeText(Conserve.this,"养护完成",Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }
    private void init() {
        fangxiangadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fangxiangchoose);
        fangxiangadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        direction=fangxiangchoose[0];
        fangxiang_spinner.setAdapter(fangxiangadapter);
        fangxiang_spinner.setSelection(0, true);
        fangxiang_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1, int position,
                                       long id) {
                // TODO Auto-generated method stub
                direction = fangxiangchoose[position];
                System.out.println(position);
                Positionchoose = position;
                System.out.println(Positionchoose);
                Log.e("TAG", direction);
                if (position == 0) {
                }
                if (position == 1) {
                }
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        chedaoadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, chedaochoose);
        chedaoadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lane=chedaochoose[0];
        chedao_spinner.setAdapter(chedaoadapter);
        chedao_spinner.setSelection(0, true);
        chedao_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1, int position,
                                       long id) {
                // TODO Auto-generated method stub
                lane = chedaochoose[position];
                System.out.println(position);
                Positionchoose = position;
                System.out.println(Positionchoose);
                Log.e("TAG", lane);
                if (position == 0) {
                }
                if (position == 1) {
                }
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        bingduadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bingduchoose);
        bingduadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        road_condition=bingduchoose[0];
        bingdu_spinner.setAdapter(bingduadapter);
        bingdu_spinner.setSelection(0, true);
        bingdu_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1, int position,
                                       long id) {
                // TODO Auto-generated method stub
                road_condition = bingduchoose[position];
                System.out.println(position);
                Positionchoose = position;
                System.out.println(Positionchoose);
                Log.e("TAG", road_condition);
                if (position == 0) {
                }
                if (position == 1) {
                }
                parent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }
}
