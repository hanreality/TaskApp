package com.punuo.sys.app.taskapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.punuo.sys.app.taskapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyTask extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.wrecker)
    Button wrecker;
    @Bind(R.id.conserve)
    Button conserve;
    private DataLoader loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);
        ButterKnife.bind(this);
        conserve.setOnClickListener(this);
        wrecker.setOnClickListener(this);
        loader=new DataLoader(this);
        loader.startBinderPool(BinderPool.BINDER_TASK);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wrecker:
                Intent wreckerIntent = new Intent(MyTask.this, Wrecker.class);
                startActivity(wreckerIntent);
                break;
            case R.id.conserve:
                Intent conserveIntent = new Intent(MyTask.this, Conserve.class);
                startActivity(conserveIntent);
                break;
        }
    }
}
