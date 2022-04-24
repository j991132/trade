package com.social.trade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TeacherPage extends AppCompatActivity {

    private Button newgame, resetgame, chart, teachernation;
    private EditText gamenumber, schoolname;
    private FirebaseFirestore db;
    private String TAG = "activity_TeacherPage";
    private String nowDate, nowgameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_page);

        MySoundPlayer.initSounds(getApplicationContext());
// 버튼
        newgame = (Button) findViewById(R.id.newgame_btn);
        resetgame = (Button) findViewById(R.id.resetgame_btn);
        chart = (Button) findViewById(R.id.chart_btn);
        teachernation = (Button) findViewById(R.id.teachernation_btn);
        TextView teachermsg = (TextView) findViewById(R.id.teacher_msg);
        TextView schoolmsg = (TextView) findViewById(R.id.school_msg);

        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.newgame_btn:

                        MySoundPlayer.play(MySoundPlayer.diring);
//날짜가져오기
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                        nowDate = dateFormat.format(date);

//다이얼로그
                        Dialog MakeGameDialog = new Dialog(TeacherPage.this);
                        MakeGameDialog.setContentView(R.layout.makegame_dialog);

                        gamenumber = (EditText) MakeGameDialog.findViewById(R.id.gamenumber);
                        schoolname = (EditText) MakeGameDialog.findViewById(R.id.schoolname);
                        Button okbtn = (Button) MakeGameDialog.findViewById(R.id.ok);
                        Button cancle = (Button) MakeGameDialog.findViewById(R.id.cancel);

                        okbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!gamenumber.equals(null) && !schoolname.equals(null)) {
                                    MySoundPlayer.play(MySoundPlayer.diring);
                                    schoolmsg.setText(String.valueOf(schoolname.getText()));
                                    teachermsg.setText(String.valueOf(gamenumber.getText()));
                                    nowgameId = nowDate + schoolname.getText() + gamenumber.getText();
                                    Log.e("게임아이디", nowgameId);
                                    MakeDb(nowgameId);
                                    MakeGameDialog.dismiss();
                                } else {
                                    MySoundPlayer.play(MySoundPlayer.b);
                                    Toast.makeText(getApplication(), "학교이름 또는 방번호가 비어있습니다", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        cancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MySoundPlayer.play(MySoundPlayer.diring);
                                MakeGameDialog.dismiss();
                            }
                        });
                        MakeGameDialog.show();
                        MySoundPlayer.play(MySoundPlayer.confirm);


                        break;
                    case R.id.resetgame_btn:

                        MySoundPlayer.play(MySoundPlayer.diring);

                        break;
                    case R.id.chart_btn:

                        MySoundPlayer.play(MySoundPlayer.diring);

                        break;
                    case R.id.teachernation_btn:

                        MySoundPlayer.play(MySoundPlayer.diring);

                        break;
                }
            }


        }; //버튼 리스너
        newgame.setOnClickListener(Listener);
        resetgame.setOnClickListener(Listener);
        chart.setOnClickListener(Listener);
        teachernation.setOnClickListener(Listener);
    }

    private void MakeDb(String gameId) {
        //나라선택여부 해시맵저장
        final Map<String, Object> selectednation = new HashMap<>();
        selectednation.put("nation1", 0);
        selectednation.put("nation2", 0);
        selectednation.put("nation3", 0);
        selectednation.put("nation4", 0);
        selectednation.put("nation5", 0);
        selectednation.put("nation6", 0);
//한국자원 해시맵저장
        final Map<String, Object> kor = new HashMap<>();
        kor.put("lv", 0);
        kor.put("oil", 0);
        kor.put("fe", 10);
        kor.put("gold", 0);
        kor.put("wood", 25);
        kor.put("man", 10);
        kor.put("money", 1400);
        kor.put("request", 0);
        kor.put("myallow", 0);
        kor.put("mysource", 0);
        kor.put("mysourcenum", 0);
        kor.put("yourallow", 0);
        kor.put("yoursource", 0);
        kor.put("yoursourcenum", 0);
        kor.put("0", "0");
        kor.put("tech", 0);
        kor.put("myallow2", 0);
//중국자원 해시맵저장
        final Map<String, Object> cha = new HashMap<>();
        cha.put("lv", 0);
        cha.put("oil", 0);
        cha.put("fe", 10);
        cha.put("gold", 10);
        cha.put("wood", 0);
        cha.put("man", 120);
        cha.put("money", 500);
        cha.put("request", 0);
        cha.put("myallow", 0);
        cha.put("mysource", 0);
        cha.put("mysourcenum", 0);
        cha.put("yourallow", 0);
        cha.put("yoursource", 0);
        cha.put("yoursourcenum", 0);
        cha.put("0", "0");
        cha.put("tech", 0);
        cha.put("myallow2", 0);
//호주자원 해시맵저장
        final Map<String, Object> os = new HashMap<>();
        os.put("lv", 0);
        os.put("oil", 5);
        os.put("fe", 120);
        os.put("gold", 5);
        os.put("wood", 0);
        os.put("man", 10);
        os.put("money", 500);
        os.put("request", 0);
        os.put("myallow", 0);
        os.put("mysource", 0);
        os.put("mysourcenum", 0);
        os.put("yourallow", 0);
        os.put("yoursource", 0);
        os.put("yoursourcenum", 0);
        os.put("0", "0");
        os.put("tech", 0);
        os.put("myallow2", 0);
//캐나다자원 해시맵저장
        final Map<String, Object> ca = new HashMap<>();
        ca.put("lv", 0);
        ca.put("oil", 0);
        ca.put("fe", 10);
        ca.put("gold", 0);
        ca.put("wood", 30);
        ca.put("man", 20);
        ca.put("money", 1300);
        ca.put("request", 0);
        ca.put("myallow", 0);
        ca.put("mysource", 0);
        ca.put("mysourcenum", 0);
        ca.put("yourallow", 0);
        ca.put("yoursource", 0);
        ca.put("yoursourcenum", 0);
        ca.put("0", "0");
        ca.put("tech", 0);
        ca.put("myallow2", 0);
//사우디자원 해시맵저장
        final Map<String, Object> saudi = new HashMap<>();
        saudi.put("lv", 0);
        saudi.put("oil", 100);
        saudi.put("fe", 0);
        saudi.put("gold", 5);
        saudi.put("wood", 0);
        saudi.put("man", 10);
        saudi.put("money", 700);
        saudi.put("request", 0);
        saudi.put("myallow", 0);
        saudi.put("mysource", 0);
        saudi.put("mysourcenum", 0);
        saudi.put("yourallow", 0);
        saudi.put("yoursource", 0);
        saudi.put("yoursourcenum", 0);
        saudi.put("0", "0");
        saudi.put("tech", 0);
        saudi.put("myallow2", 0);
//남아공자원 해시맵저장
        final Map<String, Object> sa = new HashMap<>();
        sa.put("lv", 0);
        sa.put("oil", 5);
        sa.put("fe", 0);
        sa.put("gold", 85);
        sa.put("wood", 0);
        sa.put("man", 10);
        sa.put("money", 900);
        sa.put("request", 0);
        sa.put("myallow", 0);
        sa.put("mysource", 0);
        sa.put("mysourcenum", 0);
        sa.put("yourallow", 0);
        sa.put("yoursource", 0);
        sa.put("yoursourcenum", 0);
        sa.put("0", "0");
        sa.put("tech", 0);
        sa.put("myallow2", 0);

        db = FirebaseFirestore.getInstance();


        DocumentReference docRef = db.collection(nowgameId).document("selectednation");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "스탭샷 데이터" + document.getData());
                    } else {
                        Log.d(TAG, "도큐먼트 찾을 수 없음");
                        db.collection(nowgameId).document("selectednation")
                                .set(selectednation)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("activity_tradegame", "기록이 성공함");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("activity_tradegame", "쓰기 실패", e);
                                    }
                                });

                        db.collection(nowgameId).document("대한민국")
                                .set(kor)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("activity_tradegame", "기록이 성공함");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("activity_tradegame", "쓰기 실패", e);
                                    }
                                });

                        db.collection(nowgameId).document("중국")
                                .set(cha)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("activity_tradegame", "기록이 성공함");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("activity_tradegame", "쓰기 실패", e);
                                    }
                                });

                        db.collection(nowgameId).document("호주")
                                .set(os)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("activity_tradegame", "기록이 성공함");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("activity_tradegame", "쓰기 실패", e);
                                    }
                                });

                        db.collection(nowgameId).document("캐나다")
                                .set(ca)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("activity_tradegame", "기록이 성공함");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("activity_tradegame", "쓰기 실패", e);
                                    }
                                });

                        db.collection(nowgameId).document("사우디아라비아")
                                .set(saudi)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("activity_tradegame", "기록이 성공함");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("activity_tradegame", "쓰기 실패", e);
                                    }
                                });

                        db.collection(nowgameId).document("남아프리카공화국")
                                .set(sa)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("activity_tradegame", "기록이 성공함");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("activity_tradegame", "쓰기 실패", e);
                                    }
                                });
                    }
                } else {
                    Log.d(TAG, "가져오기 실패", task.getException());
                }
            }
        });
    }

}