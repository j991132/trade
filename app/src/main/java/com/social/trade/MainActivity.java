package com.social.trade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Button connect;
    private Button finish, teacherrulebtn, howtorulebtn;
    private MediaPlayer mediaPlayer;
    private FirebaseFirestore db;
    private Intent intent = null;
    private String shared = "file";
    private EditText ename,gamenum,sname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

//오프닝 음악
        mediaPlayer = MediaPlayer.create(this, R.raw.reopening);
        mediaPlayer.start();
// 효과음 으로는 안돌아가더라 로딩하고 시간이 필요한 듯
       MySoundPlayer.initSounds(getApplicationContext());

        db = FirebaseFirestore.getInstance();

        connect = (Button) findViewById(R.id.button1);
        finish = (Button) findViewById(R.id.button2);
        teacherrulebtn = (Button)findViewById(R.id.teacherrulebtn);
        howtorulebtn = (Button)findViewById(R.id.howtorulebtn);
        connect.setEnabled(true);
        finish.setEnabled(false);
        ename = (EditText) findViewById(R.id.editText);
        gamenum = (EditText) findViewById(R.id.gamenum);
        sname = (EditText) findViewById(R.id.sname);
//로그인 값 불러오기
        SharedPreferences sharedPreferences = getSharedPreferences(shared,0);
        String schoolname = sharedPreferences.getString("schoolname","");
        String gamenumname = sharedPreferences.getString("gamenumname","");
        String teamname = sharedPreferences.getString("teamname","");
        sname.setText(schoolname);
        gamenum.setText(gamenumname);
        ename.setText(teamname);



        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                String nowDate = dateFormat.format(date);

                if (String.valueOf(ename.getText()).trim().equals("teacher") || String.valueOf(ename.getText()).trim().equals("t") || String.valueOf(ename.getText()).trim().equals("선생님")) {

//선생님 화면으로 인텐트
                    intent = new Intent(MainActivity.this, TeacherPage.class)
                            .setAction(Intent.ACTION_MAIN)
                            .addCategory(Intent.CATEGORY_LAUNCHER)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ;
                    startActivity(intent);
                    connect.setEnabled(false);
                    finish.setEnabled(true);

                } else if (!String.valueOf(ename.getText()).trim().equals("teacher") && !String.valueOf(ename.getText()).trim().equals("t") && !String.valueOf(ename.getText()).trim().equals("선생님") && !String.valueOf(gamenum.getText()).trim().equals(null) && !String.valueOf(sname.getText()).trim().equals(null) && !String.valueOf(ename.getText()).trim().equals("")) {
                    String nowgameId = nowDate + sname.getText().toString().trim() + gamenum.getText().toString().trim();

                    db.collection(nowgameId).document("selectednation").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if(document.getData() != null) {
                                    Log.e("메인액티비티 도큐먼트 존재여부", String.valueOf(document));



                                    intent = new Intent(MainActivity.this, tradegame.class)
                                            .setAction(Intent.ACTION_MAIN)
                                            .addCategory(Intent.CATEGORY_LAUNCHER)
                                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                    intent.putExtra("ename", String.valueOf(ename.getText()));
                                    intent.putExtra("gameId", nowgameId);
                                    startActivity(intent);
                                    connect.setEnabled(false);
                                    finish.setEnabled(true);
                                }else{
                                    MySoundPlayer.play(MySoundPlayer.b);
                                    Toast.makeText(getApplication(), "위 이름으로 된 게임이 존재하지 않거나 모둠이름이 없습니다.", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                Log.e("메인액티비티 게임존재여부", String.valueOf(task.getException()));

                            }
                        }
                    });


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
        //선생님 도움말 버튼
        teacherrulebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        MySoundPlayer.play(MySoundPlayer.diring);
//다이얼로그생성
                        final Dialog teacherruledialog = new Dialog( MainActivity.this );
//다이얼로그 화면 꽉차게
                        // 액티비티의 타이틀바를 숨긴다.
                        teacherruledialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        Window window = teacherruledialog.getWindow();
                        Rect displayRectangle = new Rect();
                        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                        // inflate and adjust layout
                        LayoutInflater inflater = (LayoutInflater)MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View layout = inflater.inflate(R.layout.teacherruledialog, null);
                        layout.setMinimumWidth((int)(displayRectangle.width() * 0.9f));
                        layout.setMinimumHeight((int)(displayRectangle.height() * 0.9f));
                        teacherruledialog.setContentView(layout);

                        teacherruledialog.setCancelable(false);

                        Button teacherrulecanale = (Button) teacherruledialog.findViewById(R.id.teacherrulecanclebtn);
                        teacherrulecanale.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MySoundPlayer.play(MySoundPlayer.diring);
                                teacherruledialog.dismiss();
                            }
                        });
                        teacherruledialog.show();
                        MySoundPlayer.play(MySoundPlayer.confirm);



            }
        });
 //게임규칙 버튼
        howtorulebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySoundPlayer.play(MySoundPlayer.diring);
//다이얼로그생성
                final Dialog howtoruledialog = new Dialog( MainActivity.this );
//다이얼로그 화면 꽉차게
                // 액티비티의 타이틀바를 숨긴다.
                howtoruledialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                Window window = howtoruledialog.getWindow();
                Rect displayRectangle = new Rect();
                window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                // inflate and adjust layout
                LayoutInflater inflater = (LayoutInflater)MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.howtodialog, null);
                layout.setMinimumWidth((int)(displayRectangle.width() * 0.9f));
                layout.setMinimumHeight((int)(displayRectangle.height() * 0.9f));
                howtoruledialog.setContentView(layout);

                howtoruledialog.setCancelable(false);

                Button howtorulecanale = (Button) howtoruledialog.findViewById(R.id.howtocanclebtn);
                howtorulecanale.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MySoundPlayer.play(MySoundPlayer.diring);
                        howtoruledialog.dismiss();
                    }
                });
                howtoruledialog.show();
                MySoundPlayer.play(MySoundPlayer.confirm);
            }
        });
//        MySoundPlayer.play(MySoundPlayer.opening);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences  sharedPreferences = getSharedPreferences(shared, 0);
        SharedPreferences.Editor editor  = sharedPreferences.edit();
        String schoolname = sname.getText().toString().trim();
        String gamenumname = gamenum.getText().toString().trim();
        String teamname = ename.getText().toString().trim();
        editor.putString("schoolname", schoolname);
        editor.putString("gamenumname", gamenumname);
        editor.putString("teamname", teamname);
        editor.apply();

    }
}
