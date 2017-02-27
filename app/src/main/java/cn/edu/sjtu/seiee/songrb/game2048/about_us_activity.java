package cn.edu.sjtu.seiee.songrb.game2048;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

public class about_us_activity extends AppCompatActivity {

    private Button my_button2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(cn.edu.sjtu.seiee.songrb.game2048.R.layout.activity_about_us);
        Toolbar myToolbar = (Toolbar) findViewById(cn.edu.sjtu.seiee.songrb.game2048.R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }
}