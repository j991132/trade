package com.social.trade;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class tradegame extends AppCompatActivity {

    private FirebaseFirestore db;
    private String TAG = "activity_tradegame";
    private TextView testtext;
    private String gameId;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_tradegame);

        MySoundPlayer.initSounds(getApplicationContext());


        final Button nationbtn1 = (Button) findViewById(R.id.nationbtn1);
        Button nationbtn2 = (Button) findViewById(R.id.nationbtn2);
        Button nationbtn3 = (Button) findViewById(R.id.nationbtn3);
        Button nationbtn4 = (Button) findViewById(R.id.nationbtn4);
        Button nationbtn5 = (Button) findViewById(R.id.nationbtn5);
        Button nationbtn6 = (Button) findViewById(R.id.nationbtn6);
        testtext = (TextView) findViewById(R.id.testtext);

        Intent intent = getIntent();
        name = intent.getStringExtra("ename").trim();
        gameId = intent.getStringExtra("gameId").trim();
        Log.e(TAG, "게임아이디 넘어온 값   "+gameId);
/*
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


        DocumentReference docRef = db.collection("나라선택여부").document("selectednation");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        Log.d(TAG, "스탭샷 데이터"+document.getData());
                    }else{
                        Log.d(TAG,"도큐먼트 찾을 수 없음");
                        db.collection("나라선택여부").document("selectednation")
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
                                        Log.w("activity_tradegame", "쓰기 실패",e);
                                    }
                                });

                        db.collection("나라선택여부").document("대한민국")
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
                                        Log.w("activity_tradegame", "쓰기 실패",e);
                                    }
                                });

                        db.collection("나라선택여부").document("중국")
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
                                        Log.w("activity_tradegame", "쓰기 실패",e);
                                    }
                                });

                        db.collection("나라선택여부").document("호주")
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
                                        Log.w("activity_tradegame", "쓰기 실패",e);
                                    }
                                });

                        db.collection("나라선택여부").document("캐나다")
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
                                        Log.w("activity_tradegame", "쓰기 실패",e);
                                    }
                                });

                        db.collection("나라선택여부").document("사우디아라비아")
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
                                        Log.w("activity_tradegame", "쓰기 실패",e);
                                    }
                                });

                        db.collection("나라선택여부").document("남아프리카공화국")
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
                                        Log.w("activity_tradegame", "쓰기 실패",e);
                                    }
                                });
                    }
                }else{
                    Log.d(TAG, "가져오기 실패", task.getException());
                }
            }
        });
*/
        db = FirebaseFirestore.getInstance();
//  testtext.setText("내가 선택할 나라는?");
        testtext.setText("우리모둠 이름: "+name);

//실시간 데이터 업데이트 감지하기
        final DocumentReference docRef1 = db.collection(gameId).document("selectednation");
        docRef1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }


                if (snapshot != null && snapshot.exists()) {

                    Object n1 = snapshot.getData().get("nation1").toString();
                    Object n2 = snapshot.getData().get("nation2").toString();
                    Object n3 = snapshot.getData().get("nation3").toString();
                    Object n4 = snapshot.getData().get("nation4").toString();
                    Object n5 = snapshot.getData().get("nation5").toString();
                    Object n6 = snapshot.getData().get("nation6").toString();
                    Log.e(TAG, n1+""+n2+""+n3+""+n4+""+n5+""+n6);
                        if (n1.equals("0") || n1.equals(name)){
                            nationbtn1.setEnabled(true);
                            Log.e(TAG, "네이션1 true   "+snapshot.getData().get("nation1").toString());
                        }else{
                            nationbtn1.setEnabled(false);
                            Log.e(TAG, "네이션1 false   "+snapshot.getData().get("nation1").toString());
                        }
                    if (n2.equals("0") || n2.equals(name)){
                        nationbtn2.setEnabled(true);

                    }else{
                        nationbtn2.setEnabled(false);
                    }
                    if (n3.equals("0") || n3.equals(name)){
                        nationbtn3.setEnabled(true);

                    }else{
                        nationbtn3.setEnabled(false);
                    }
                    if (n4.equals("0") || n4.equals(name)){
                        nationbtn4.setEnabled(true);

                    }else{
                        nationbtn4.setEnabled(false);
                    }
                    if (n5.equals("0") || n5.equals(name)){
                        nationbtn5.setEnabled(true);

                    }else{
                        nationbtn5.setEnabled(false);
                    }
                    if (n6.equals("0") || n6.equals(name)){
                        nationbtn6.setEnabled(true);

                    }else{
                        nationbtn6.setEnabled(false);
                    }



                    Object n = snapshot.getData().get("nation6").toString();

                    Log.e(TAG, "Current data: " + snapshot.getData());
                    Toast.makeText(getApplication(), "Current data: " + snapshot.getData(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });

//버튼 액션
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.nationbtn1:
                        MySoundPlayer.play(MySoundPlayer.diring);
                        select("nation1", name, "대한민국");


 //                       Toast.makeText(getApplication(), "첫번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nationbtn2:
                        MySoundPlayer.play(MySoundPlayer.diring);
                        select("nation2", name, "중국");



 //                       Toast.makeText(getApplication(), "두번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nationbtn3:
                        MySoundPlayer.play(MySoundPlayer.diring);
                        select("nation3", name, "호주");
 //                       Toast.makeText(getApplication(), "세번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nationbtn4:
                        MySoundPlayer.play(MySoundPlayer.diring);
                        select("nation4", name, "캐나다");
 //                       Toast.makeText(getApplication(), "네번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nationbtn5:
                        MySoundPlayer.play(MySoundPlayer.diring);
                        select("nation5", name, "사우디아라비아");
//                        Toast.makeText(getApplication(), "다섯번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nationbtn6:
                        MySoundPlayer.play(MySoundPlayer.diring);
                        select("nation6", name, "남아프리카공화국");
 //                       Toast.makeText(getApplication(), "여섯번째 버튼입니다.", Toast.LENGTH_SHORT).show();
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

//나라 선택시 선택여부 데이터 갱신
    public void select(final String nationnum, final String intentname, final String nationname ) {
        db.collection(gameId).document("selectednation")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            Object s = document.getData().get(nationnum).toString();
//                            testtext.setText("기록된 이름: "+s);

                            if (s.equals("0")){
                                Log.d(TAG, "기록이 성공함"+s);


                                db.collection(gameId).document("selectednation")
                                        .update(nationnum, intentname)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                nationstate(nationname);
                                                Log.d(TAG, "필드 업데이트 성공함");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "쓰기 실패",e);
                                            }
                                        });
//모둠이름 업데이트하기
                                db.collection(gameId).document(nationname)
                                        .update("teamname", name)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("activity_tradegame", "기록이 성공함");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("activity_tradegame", "쓰기 실패",e);
                                            }
                                        });

                            }else{
                                Log.d(TAG,"이미 선택된 버튼임 - 선택자:  "+s);
                                if(s.equals(intentname)){
                                    //액티비티 1번 나라로 넘기기
                                    nationstate(nationname);
                                    Toast.makeText(getApplication(), nationnum+" 국가로 이동.", Toast.LENGTH_SHORT).show();
                                }else{
                                    MySoundPlayer.play(MySoundPlayer.b);
                                    Log.d(TAG,"선택자  "+s);
                                    Toast.makeText(getApplication(), "이미 선택된 나라입니다.", Toast.LENGTH_SHORT).show();
                                }


                            }

                        }else{
                            MySoundPlayer.play(MySoundPlayer.b);
                            Log.d(TAG, "가져오기 실패", task.getException());
                            Toast.makeText(getApplication(), "이미 선택된 나라입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

//나라상태창으로 데이터 인텐트
    public void nationstate(final String nationname) {
        Intent intent = new Intent(tradegame.this, nation.class);
        intent.putExtra("nationname", nationname);
        intent.putExtra("gameId", gameId);
        startActivity(intent);
    }



    public void onBackButtonClicked(View v){
        finish();
    }
}
