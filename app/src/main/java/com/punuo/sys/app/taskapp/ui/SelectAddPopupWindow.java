package com.punuo.sys.app.taskapp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.punuo.sys.app.taskapp.R;

import java.io.File;

public class SelectAddPopupWindow extends PopupWindow implements View.OnClickListener {
    LinearLayout videoView;
    LinearLayout videoRecord;
    LinearLayout photo;
    private View mMenuView;
    private Activity context;
    public static final String IMAGE_PATH = "DCIM";
    private static final int FLAG_CHOOSE_PHONE = 6;
    public static String localTempLuxiangFileName;
    public static String localTempImageFileName;
    public static final File FILE_SDCARD = Environment
            .getExternalStorageDirectory();
    public static final File FILE_LOCAL = new File(FILE_SDCARD, IMAGE_PATH);
    public static final File FILE_PIC_SCREENSHOT = new File(FILE_LOCAL,
            "Camera");
    public static final File FILE_PIC_Luxiang = new File(FILE_LOCAL,
            "/LuXiang/");
    private String status = Environment.getExternalStorageState();
    private String name = "LuXiang";
    private String path = "/sdcard/pn_ip4g_h2_third/LuXiang";

    public SelectAddPopupWindow(final Activity context) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.addxml, null);

        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);

        videoView = (LinearLayout) mMenuView.findViewById(R.id.video_view);
        videoRecord = (LinearLayout) mMenuView.findViewById(R.id.video_record);
        photo = (LinearLayout) mMenuView.findViewById(R.id.photo);

        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w / 2 + 50);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.mystyle);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout2).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
        videoView.setOnClickListener(this);
        videoRecord.setOnClickListener(this);
        photo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_view:
                context.startActivity(new Intent(context, AlbumAty.class));
                break;
            case R.id.video_record:
                Intent intent=new Intent(context,MyCamera.class);
                intent.putExtra("type",1);
                context.startActivity(intent);
                break;
            case R.id.photo:
                context.startActivity(new Intent(context,MyCamera.class));
                break;
        }
    }
}
