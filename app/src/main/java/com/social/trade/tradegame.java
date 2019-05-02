package com.social.trade;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class tradegame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tradegame);

        Button nationbtn1 = (Button) findViewById(R.id.nationbtn1);
        Button nationbtn2 = (Button) findViewById(R.id.nationbtn2);
        Button nationbtn3 = (Button) findViewById(R.id.nationbtn3);
        Button nationbtn4 = (Button) findViewById(R.id.nationbtn4);
        Button nationbtn5 = (Button) findViewById(R.id.nationbtn5);
        Button nationbtn6 = (Button) findViewById(R.id.nationbtn6);

        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.nationbtn1:
                        Toast.makeText(getApplication(), "첫번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nationbtn2:
                        Toast.makeText(getApplication(), "두번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nationbtn3:
                        Toast.makeText(getApplication(), "세번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nationbtn4:
                        Toast.makeText(getApplication(), "네번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nationbtn5:
                        Toast.makeText(getApplication(), "다섯번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nationbtn6:
                        Toast.makeText(getApplication(), "여섯번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        nationbtn1.setOnClickListener(Listener);
        nationbtn2.setOnClickListener(Listener);
        nationbtn3.setOnClickListener(Listener);
        nationbtn4.setOnClickListener(Listener);
        nationbtn5.setOnClickListener(Listener);
        nationbtn6.setOnClickListener(Listener);



    }
    public void onBackButtonClicked(View v){
        finish();
    }
}
