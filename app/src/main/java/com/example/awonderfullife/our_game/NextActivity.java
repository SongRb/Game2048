package com.example.awonderfullife.our_game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class NextActivity extends Activity {

    private Button my_button2 = null;
    private Button my_button3 = null;
    private Button my_button4 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        my_button2 = (Button) findViewById(R.id.top_button);
        my_button2.setOnClickListener(new MyButtonListener());

        my_button3 = (Button) findViewById(R.id.mid_button);
        my_button3.setOnClickListener(new MyButtonListener2());

        my_button4 = (Button) findViewById(R.id.left_button);
        my_button4.setOnClickListener(new MyButtonListener3());
    }

    class MyButtonListener implements OnClickListener {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(NextActivity.this, MainActivity.class);
            NextActivity.this.startActivity(intent);
        }
    }

    class MyButtonListener2 implements OnClickListener {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(NextActivity.this, MainActivity_3.class);
            NextActivity.this.startActivity(intent);
        }
    }

    class MyButtonListener3 implements OnClickListener {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent intent = new Intent();
            intent.setClass(NextActivity.this, BlueRatio.class);
            NextActivity.this.startActivity(intent);
        }
    }
}

