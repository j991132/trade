package com.social.trade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class nation extends AppCompatActivity {

    private TextView name;
    private ImageView nationmark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_nation);

        Intent intent = getIntent();
        final String nationname = intent.getStringExtra("nationname");

        name = (TextView) findViewById(R.id.Textnation);
        nationmark = (ImageView) findViewById(R.id.nationmark);

        switch (nationname){
            case "kor":
         //       nationmark.setImageBitmap();
                name.setText("대한민국");
                break;
        }
    }
}
