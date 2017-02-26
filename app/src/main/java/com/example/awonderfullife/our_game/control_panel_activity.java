package com.example.awonderfullife.our_game;

import android.app.Activity;
import android.content.Intent;
import android.view.View;//注意view的大小写
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.Button;

public class control_panel_activity extends Activity {

    private Button my_button = null;
    private Button my_button2 = null;
    private Button my_button3 = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helloworld);

        my_button = (Button) findViewById(R.id.my_button);
        my_button.setText("开始游戏");
        my_button.setOnClickListener(new MyButtonListener());

        my_button2 = (Button) findViewById(R.id.left_button);
        my_button2.setText("操作说明");
        my_button2.setOnClickListener(new MyButtonListener2());

        my_button3 = (Button) findViewById(R.id.mid_button);
        my_button3.setText("关于我们");
        my_button3.setOnClickListener(new MyButtonListener3());

        Button below_button = (Button) findViewById(R.id.below_button);
        below_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    class MyButtonListener implements OnClickListener {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(control_panel_activity.this, NextActivity.class);
            control_panel_activity.this.startActivity(intent);
        }
    }

    class MyButtonListener2 implements OnClickListener {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(control_panel_activity.this, NextActivity2.class);
            control_panel_activity.this.startActivity(intent);
        }
    }

    class MyButtonListener3 implements OnClickListener {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(control_panel_activity.this, about_us_activity.class);
            control_panel_activity.this.startActivity(intent);
        }
    }

    /**
     * 如果下面的语句不要，那么系统运行的时候会直接进入本程序中，而不是先进入主菜单
     * 再进入选择应用程序界面进入本程序
     * 为了方便调试，这里就不进入主菜单界面了*/
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_helloworld, menu);
        return true;
    }*/
}
