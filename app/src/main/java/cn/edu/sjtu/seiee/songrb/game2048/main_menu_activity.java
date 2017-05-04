package cn.edu.sjtu.seiee.songrb.game2048;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class main_menu_activity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(cn.edu.sjtu.seiee.songrb.game2048.R.layout.activity_main_menu);

        Button my_button = (Button) findViewById(R.id.achievement_button);

        my_button.setOnClickListener(new MyButtonListener());

        my_button = (Button) findViewById(cn.edu.sjtu.seiee.songrb.game2048.R.id.tutorial_button);

        my_button.setOnClickListener(new MyButtonListener2());

        my_button = (Button) findViewById(cn.edu.sjtu.seiee.songrb.game2048.R.id.about_us_button);

        my_button.setOnClickListener(new MyButtonListener3());

        Button below_button = (Button) findViewById(cn.edu.sjtu.seiee.songrb.game2048.R.id.exit_button);
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
            intent.setClass(main_menu_activity.this, game_menu_activity.class);
            main_menu_activity.this.startActivity(intent);
        }
    }

    class MyButtonListener2 implements OnClickListener {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(main_menu_activity.this, guideline_activity.class);
            main_menu_activity.this.startActivity(intent);
        }
    }

    class MyButtonListener3 implements OnClickListener {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(main_menu_activity.this, about_us_activity.class);
            main_menu_activity.this.startActivity(intent);
        }
    }

    /**
     * 如果下面的语句不要，那么系统运行的时候会直接进入本程序中，而不是先进入主菜单
     * 再进入选择应用程序界面进入本程序
     * 为了方便调试，这里就不进入主菜单界面了*/
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }*/
}
