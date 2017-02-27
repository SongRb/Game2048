package cn.edu.sjtu.seiee.songrb.game2048;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class game_menu_activity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(cn.edu.sjtu.seiee.songrb.game2048.R.layout.activity_game_menu);

        Button my_button2 = null;
        Button my_button3 = null;
        Button my_button4 = null;

        my_button2 = (Button) findViewById(cn.edu.sjtu.seiee.songrb.game2048.R.id.top_button);
        my_button2.setOnClickListener(new MyButtonListener());

        my_button3 = (Button) findViewById(cn.edu.sjtu.seiee.songrb.game2048.R.id.mid_button);
        my_button3.setOnClickListener(new MyButtonListener2());

        my_button4 = (Button) findViewById(cn.edu.sjtu.seiee.songrb.game2048.R.id.left_button);
        my_button4.setOnClickListener(new MyButtonListener3());
    }

    class MyButtonListener implements OnClickListener {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(game_menu_activity.this, MainActivity.class);
            game_menu_activity.this.startActivity(intent);
        }
    }

    class MyButtonListener2 implements OnClickListener {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(game_menu_activity.this, MainActivity_3.class);
            game_menu_activity.this.startActivity(intent);
        }
    }

    class MyButtonListener3 implements OnClickListener {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(game_menu_activity.this, BlueRatio.class);
            game_menu_activity.this.startActivity(intent);
        }
    }
}

