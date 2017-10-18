package com.punuo.sys.app.taskapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.punuo.sys.app.taskapp.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by acer on 2016/11/15.
 */

public class VideoLook extends AppCompatActivity {
    @Bind(R.id.play)
    FullScreenVideoView play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videolook);
        ButterKnife.bind(this);
        Intent i=getIntent();
        String path=i.getStringExtra("Path");
        play.setVideoPath(path);
        play.start();
    }
}
