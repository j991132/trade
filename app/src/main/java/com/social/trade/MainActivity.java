package com.social.trade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button connect;
    private Button finish;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);



        connect = (Button)findViewById(R.id.button1);
        finish =  (Button)findViewById(R.id.button2);
        connect.setEnabled(true);
        finish.setEnabled(false);
        final EditText ename= (EditText) findViewById(R.id.editText);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, tradegame.class);
                intent.putExtra("ename", String.valueOf(ename.getText()));
                startActivity(intent);
                connect.setEnabled(false);
                finish.setEnabled(true);
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect.setEnabled(true);
                finish.setEnabled(false);
            }
        });
    }
}
