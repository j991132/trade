package com.social.trade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Button connect;
    private Button finish;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

//오프닝 음악
        mediaPlayer = MediaPlayer.create(this, R.raw.reopening);
        mediaPlayer.start();
// 효과음 으로는 안돌아가더라 로딩하고 시간이 필요한 듯
//       MySoundPlayer.initSounds(getApplicationContext());


        connect = (Button) findViewById(R.id.button1);
        finish = (Button) findViewById(R.id.button2);
        connect.setEnabled(true);
        finish.setEnabled(false);
        final EditText ename = (EditText) findViewById(R.id.editText);
        EditText gamenum = (EditText) findViewById(R.id.gamenum);
        EditText sname = (EditText) findViewById(R.id.sname);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = null;
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                String nowDate = dateFormat.format(date);

                if (String.valueOf(ename.getText()).trim().equals("teacher")) {

//선생님 화면으로 인텐트
                    intent = new Intent(MainActivity.this, TeacherPage.class);
                    startActivity(intent);
                    connect.setEnabled(false);
                    finish.setEnabled(true);

                } else if (!String.valueOf(ename.getText()).trim().equals("teacher") && !String.valueOf(gamenum.getText()).trim().equals(null) && !String.valueOf(sname.getText()).trim().equals(null)) {
                    String nowgameId = nowDate + sname.getText().toString().trim() + gamenum.getText().toString().trim();
                    intent = new Intent(MainActivity.this, tradegame.class);
                    intent.putExtra("ename", String.valueOf(ename.getText()));
                    intent.putExtra("gameId", nowgameId);
                    startActivity(intent);
                    connect.setEnabled(false);
                    finish.setEnabled(true);
                } else {
                    Toast.makeText(getApplication(), "학교이름 또는 방번호 또는 모둠이름이 비어있습니다", Toast.LENGTH_SHORT).show();
                }

            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect.setEnabled(true);
                finish.setEnabled(false);
            }
        });
//        MySoundPlayer.play(MySoundPlayer.opening);
    }
}
