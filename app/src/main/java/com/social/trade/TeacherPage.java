package com.social.trade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private Intent intent;
    private String shared = "file";
    private TextView teachermsg, schoolmsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_page);

        MySoundPlayer.initSounds(getApplicationContext());

        //날짜가져오기
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        nowDate = dateFormat.format(date);

       // 버튼
        newgame = (Button) findViewById(R.id.newgame_btn);
        resetgame = (Button) findViewById(R.id.resetgame_btn);
        chart = (Button) findViewById(R.id.chart_btn);
        teachernation = (Button) findViewById(R.id.teachernation_btn);
        teachermsg = (TextView) findViewById(R.id.teacher_msg);
        schoolmsg = (TextView) findViewById(R.id.school_msg);

        //로그인 값 불러오기
        SharedPreferences sharedPreferences = getSharedPreferences(shared,0);
        String tschoolname = sharedPreferences.getString("schoolname","");
        String tgamenumname = sharedPreferences.getString("gamenumname","");


        schoolmsg.setText(tschoolname);
        teachermsg.setText(tgamenumname);
        nowgameId = nowDate + schoolmsg.getText().toString().trim() + teachermsg.getText().toString().trim();

        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.newgame_btn:

                        MySoundPlayer.play(MySoundPlayer.diring);


//다이얼로그
                        Dialog MakeGameDialog = new Dialog(TeacherPage.this);
                        MakeGameDialog.setContentView(R.layout.makegame_dialog);
                        MakeGameDialog.setCancelable(false);

                        gamenumber = (EditText) MakeGameDialog.findViewById(R.id.gamenumber);
                        schoolname = (EditText) MakeGameDialog.findViewById(R.id.schoolname);
                        Button okbtn = (Button) MakeGameDialog.findViewById(R.id.ok);
                        Button cancle = (Button) MakeGameDialog.findViewById(R.id.cancel);

                        okbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!gamenumber.equals(null) && !schoolname.equals(null)) {
                                    MySoundPlayer.play(MySoundPlayer.diring);
                                    schoolmsg.setText(String.valueOf(schoolname.getText()).trim());
                                    teachermsg.setText(String.valueOf(gamenumber.getText()));
                                    nowgameId = nowDate + schoolname.getText().toString().trim() + gamenumber.getText().toString().trim();
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

                        //다이얼로그
                        Dialog ResetGameDialog = new Dialog(TeacherPage.this);
                        ResetGameDialog.setContentView(R.layout.confirmdialog);
                        ResetGameDialog.setCancelable(false);

                        Button okbtn2 = (Button) ResetGameDialog.findViewById(R.id.ok);
                        Button cancle2 = (Button) ResetGameDialog.findViewById(R.id.cancel);
                        TextView meg = (TextView) ResetGameDialog.findViewById(R.id.confirmtitle);
                        meg.setText(" 게임 데이터를 초기화 하겠습니까?  ");

                        okbtn2.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                MySoundPlayer.play(MySoundPlayer.diring);

                                Log.e("게임아이디", nowgameId);
                                ResetDb(nowgameId);
                                ResetGameDialog.dismiss();


                            }
                        });
                        cancle2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MySoundPlayer.play(MySoundPlayer.diring);
                                ResetGameDialog.dismiss();
                            }
                        });
                        ResetGameDialog.show();
                        MySoundPlayer.play(MySoundPlayer.confirm);

                        break;
                    case R.id.chart_btn:

                        MySoundPlayer.play(MySoundPlayer.diring);

                        intent = new Intent(TeacherPage.this, RankList.class)
                                .setAction(Intent.ACTION_MAIN)
                                .addCategory(Intent.CATEGORY_LAUNCHER)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);;
                        intent.putExtra("gameId", nowgameId);
                        startActivity(intent);

                        break;
                    case R.id.teachernation_btn:

                        MySoundPlayer.play(MySoundPlayer.diring);

                        intent = new Intent(TeacherPage.this, nation.class)
                                .setAction(Intent.ACTION_MAIN)
                                .addCategory(Intent.CATEGORY_LAUNCHER)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);;
                        intent.putExtra("nationname", "선생님나라");
                        intent.putExtra("gameId", nowgameId);
                        startActivity(intent);

                        break;
                }
            }


        }; //버튼 리스너
        newgame.setOnClickListener(Listener);
        resetgame.setOnClickListener(Listener);
        chart.setOnClickListener(Listener);
        teachernation.setOnClickListener(Listener);
    }//본문 끝

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //로그인 기록하기
        SharedPreferences  sharedPreferences = getSharedPreferences(shared, 0);
        SharedPreferences.Editor editor  = sharedPreferences.edit();


        editor.putString("schoolname", schoolmsg.getText().toString().trim());
        editor.putString("gamenumname", teachermsg.getText().toString().trim());

        editor.apply();
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
        selectednation.put("winner", "0");
//한국자원 해시맵저장
        final Map<String, Object> kor = new HashMap<>();
        kor.put("lv", "0");
        kor.put("oil", 0);
        kor.put("fe", 10);
        kor.put("gold", 0);
        kor.put("wood", 25);
        kor.put("man", 10);
        kor.put("money", 1400);
        kor.put("request", 0);
        kor.put("myallow", 0);
        kor.put("mysource",0);
        kor.put("mysourcenum",0);
        kor.put("yourallow",0);
        kor.put("yoursource",0);
        kor.put("yoursourcenum",0);
        kor.put("0","0");
        kor.put("tech",0);
        kor.put("myallow2", 0);
        kor.put("nationName","대한민국");
        kor.put("teamname", "0");
//중국자원 해시맵저장
        final Map<String, Object> cha = new HashMap<>();
        cha.put("lv", "0");
        cha.put("oil", 0);
        cha.put("fe", 10);
        cha.put("gold", 10);
        cha.put("wood", 0);
        cha.put("man", 120);
        cha.put("money", 500);
        cha.put("request", 0);
        cha.put("myallow", 0);
        cha.put("mysource",0);
        cha.put("mysourcenum",0);
        cha.put("yourallow",0);
        cha.put("yoursource",0);
        cha.put("yoursourcenum",0);
        cha.put("0","0");
        cha.put("tech",0);
        cha.put("myallow2", 0);
        cha.put("nationName","중국");
        cha.put("teamname", "0");
//호주자원 해시맵저장
        final Map<String, Object> os = new HashMap<>();
        os.put("lv", "0");
        os.put("oil", 5);
        os.put("fe", 120);
        os.put("gold", 5);
        os.put("wood", 0);
        os.put("man", 10);
        os.put("money", 500);
        os.put("request", 0);
        os.put("myallow", 0);
        os.put("mysource",0);
        os.put("mysourcenum",0);
        os.put("yourallow",0);
        os.put("yoursource",0);
        os.put("yoursourcenum",0);
        os.put("0","0");
        os.put("tech",0);
        os.put("myallow2", 0);
        os.put("nationName","호주");
        os.put("teamname", "0");
//캐나다자원 해시맵저장
        final Map<String, Object> ca = new HashMap<>();
        ca.put("lv", "0");
        ca.put("oil", 0);
        ca.put("fe", 10);
        ca.put("gold", 0);
        ca.put("wood", 30);
        ca.put("man", 20);
        ca.put("money", 1300);
        ca.put("request", 0);
        ca.put("myallow", 0);
        ca.put("mysource",0);
        ca.put("mysourcenum",0);
        ca.put("yourallow",0);
        ca.put("yoursource",0);
        ca.put("yoursourcenum",0);
        ca.put("0","0");
        ca.put("tech",0);
        ca.put("myallow2", 0);
        ca.put("nationName","캐나다");
        ca.put("teamname", "0");
//사우디자원 해시맵저장
        final Map<String, Object> saudi = new HashMap<>();
        saudi.put("lv", "0");
        saudi.put("oil", 100);
        saudi.put("fe", 0);
        saudi.put("gold", 5);
        saudi.put("wood", 0);
        saudi.put("man", 10);
        saudi.put("money", 700);
        saudi.put("request", 0);
        saudi.put("myallow", 0);
        saudi.put("mysource",0);
        saudi.put("mysourcenum",0);
        saudi.put("yourallow",0);
        saudi.put("yoursource",0);
        saudi.put("yoursourcenum",0);
        saudi.put("0","0");
        saudi.put("tech",0);
        saudi.put("myallow2", 0);
        saudi.put("nationName","사우디아라비아");
        saudi.put("teamname", "0");
//남아공자원 해시맵저장
        final Map<String, Object> sa = new HashMap<>();
        sa.put("lv", "0");
        sa.put("oil", 5);
        sa.put("fe", 0);
        sa.put("gold", 85);
        sa.put("wood", 0);
        sa.put("man", 10);
        sa.put("money", 900);
        sa.put("request", 0);
        sa.put("myallow", 0);
        sa.put("mysource",0);
        sa.put("mysourcenum",0);
        sa.put("yourallow",0);
        sa.put("yoursource",0);
        sa.put("yoursourcenum",0);
        sa.put("0","0");
        sa.put("tech",0);
        sa.put("myallow2", 0);
        sa.put("nationName","남아프리카공화국");
        sa.put("teamname", "0");
//선생님나라 자원
        final Map<String, Object> tnara = new HashMap<>();
        tnara.put("lv", "0");
        tnara.put("oil", 10000);
        tnara.put("fe", 10000);
        tnara.put("gold", 10000);
        tnara.put("wood", 10000);
        tnara.put("man", 100000);
        tnara.put("money", 100000);
        tnara.put("request", 0);
        tnara.put("myallow", 0);
        tnara.put("mysource", 0);
        tnara.put("mysourcenum", 0);
        tnara.put("yourallow", 0);
        tnara.put("yoursource", 0);
        tnara.put("yoursourcenum", 0);
        tnara.put("0", "0");
        tnara.put("tech", 0);
        tnara.put("myallow2", 0);
        tnara.put("nationName","선생님나라");
        tnara.put("teamname", "선생님");

        db = FirebaseFirestore.getInstance();


        DocumentReference docRef = db.collection(gameId).document("selectednation");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(getApplication(), "이미 방번호 " + gamenumber.getText() + " (으)로 생성된 게임이 있습니다.", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "스탭샷 데이터" + document.getData());
                    } else {
                        Log.d(TAG, "도큐먼트 찾을 수 없음");
                        db.collection(gameId).document("selectednation")
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

                        db.collection(gameId).document("대한민국")
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

                        db.collection(gameId).document("중국")
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

                        db.collection(gameId).document("호주")
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

                        db.collection(gameId).document("캐나다")
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

                        db.collection(gameId).document("사우디아라비아")
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

                        db.collection(gameId).document("남아프리카공화국")
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
                        db.collection(gameId).document("선생님나라")
                                .set(tnara)
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

    private void ResetDb(String gameId) {
        //나라선택여부 해시맵저장
        final Map<String, Object> selectednation = new HashMap<>();
        selectednation.put("nation1", 0);
        selectednation.put("nation2", 0);
        selectednation.put("nation3", 0);
        selectednation.put("nation4", 0);
        selectednation.put("nation5", 0);
        selectednation.put("nation6", 0);
        selectednation.put("winner", "0");
//한국자원 해시맵저장
        final Map<String, Object> kor = new HashMap<>();
        kor.put("lv", "0");
        kor.put("oil", 0);
        kor.put("fe", 10);
        kor.put("gold", 0);
        kor.put("wood", 25);
        kor.put("man", 10);
        kor.put("money", 1400);
        kor.put("request", 0);
        kor.put("myallow", 0);
        kor.put("mysource",0);
        kor.put("mysourcenum",0);
        kor.put("yourallow",0);
        kor.put("yoursource",0);
        kor.put("yoursourcenum",0);
        kor.put("0","0");
        kor.put("tech",0);
        kor.put("myallow2", 0);
        kor.put("nationName","대한민국");
        kor.put("teamname", "0");
//중국자원 해시맵저장
        final Map<String, Object> cha = new HashMap<>();
        cha.put("lv", "0");
        cha.put("oil", 0);
        cha.put("fe", 10);
        cha.put("gold", 10);
        cha.put("wood", 0);
        cha.put("man", 120);
        cha.put("money", 500);
        cha.put("request", 0);
        cha.put("myallow", 0);
        cha.put("mysource",0);
        cha.put("mysourcenum",0);
        cha.put("yourallow",0);
        cha.put("yoursource",0);
        cha.put("yoursourcenum",0);
        cha.put("0","0");
        cha.put("tech",0);
        cha.put("myallow2", 0);
        cha.put("nationName","중국");
        cha.put("teamname", "0");
//호주자원 해시맵저장
        final Map<String, Object> os = new HashMap<>();
        os.put("lv", "0");
        os.put("oil", 5);
        os.put("fe", 120);
        os.put("gold", 5);
        os.put("wood", 0);
        os.put("man", 10);
        os.put("money", 500);
        os.put("request", 0);
        os.put("myallow", 0);
        os.put("mysource",0);
        os.put("mysourcenum",0);
        os.put("yourallow",0);
        os.put("yoursource",0);
        os.put("yoursourcenum",0);
        os.put("0","0");
        os.put("tech",0);
        os.put("myallow2", 0);
        os.put("nationName","호주");
        os.put("teamname", "0");
//캐나다자원 해시맵저장
        final Map<String, Object> ca = new HashMap<>();
        ca.put("lv", "0");
        ca.put("oil", 0);
        ca.put("fe", 10);
        ca.put("gold", 0);
        ca.put("wood", 30);
        ca.put("man", 20);
        ca.put("money", 1300);
        ca.put("request", 0);
        ca.put("myallow", 0);
        ca.put("mysource",0);
        ca.put("mysourcenum",0);
        ca.put("yourallow",0);
        ca.put("yoursource",0);
        ca.put("yoursourcenum",0);
        ca.put("0","0");
        ca.put("tech",0);
        ca.put("myallow2", 0);
        ca.put("nationName","캐나다");
        ca.put("teamname", "0");
//사우디자원 해시맵저장
        final Map<String, Object> saudi = new HashMap<>();
        saudi.put("lv", "0");
        saudi.put("oil", 100);
        saudi.put("fe", 0);
        saudi.put("gold", 5);
        saudi.put("wood", 0);
        saudi.put("man", 10);
        saudi.put("money", 700);
        saudi.put("request", 0);
        saudi.put("myallow", 0);
        saudi.put("mysource",0);
        saudi.put("mysourcenum",0);
        saudi.put("yourallow",0);
        saudi.put("yoursource",0);
        saudi.put("yoursourcenum",0);
        saudi.put("0","0");
        saudi.put("tech",0);
        saudi.put("myallow2", 0);
        saudi.put("nationName","사우디아라비아");
        saudi.put("teamname", "0");
//남아공자원 해시맵저장
        final Map<String, Object> sa = new HashMap<>();
        sa.put("lv", "0");
        sa.put("oil", 5);
        sa.put("fe", 0);
        sa.put("gold", 85);
        sa.put("wood", 0);
        sa.put("man", 10);
        sa.put("money", 900);
        sa.put("request", 0);
        sa.put("myallow", 0);
        sa.put("mysource",0);
        sa.put("mysourcenum",0);
        sa.put("yourallow",0);
        sa.put("yoursource",0);
        sa.put("yoursourcenum",0);
        sa.put("0","0");
        sa.put("tech",0);
        sa.put("myallow2", 0);
        sa.put("nationName","남아프리카공화국");
        sa.put("teamname", "0");

        db = FirebaseFirestore.getInstance();


        DocumentReference docRef = db.collection(gameId).document("selectednation");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        db.collection(gameId).document("selectednation")
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

                        db.collection(gameId).document("대한민국")
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

                        db.collection(gameId).document("중국")
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

                        db.collection(gameId).document("호주")
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

                        db.collection(gameId).document("캐나다")
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

                        db.collection(gameId).document("사우디아라비아")
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

                        db.collection(gameId).document("남아프리카공화국")
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


                        Log.d(TAG, "스탭샷 데이터" + document.getData());
                    } else {
                        Log.d(TAG, "도큐먼트 찾을 수 없음");
                        Toast.makeText(getApplication(), "해당번호  " + gamenumber.getText() + " (으)로 생성된 게임이 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "가져오기 실패", task.getException());
                }
            }
        });
    }

}