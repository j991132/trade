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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_tradegame);




        final Button nationbtn1 = (Button) findViewById(R.id.nationbtn1);
        Button nationbtn2 = (Button) findViewById(R.id.nationbtn2);
        Button nationbtn3 = (Button) findViewById(R.id.nationbtn3);
        Button nationbtn4 = (Button) findViewById(R.id.nationbtn4);
        Button nationbtn5 = (Button) findViewById(R.id.nationbtn5);
        Button nationbtn6 = (Button) findViewById(R.id.nationbtn6);
        testtext = (TextView) findViewById(R.id.testtext);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("ename");

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
        kor.put("allow", 0);
        kor.put("mysource",0);
        kor.put("mysourcenum",0);
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
        cha.put("allow", 0);
        cha.put("mysource",0);
        cha.put("mysourcenum",0);
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
        os.put("allow", 0);
        os.put("mysource",0);
        os.put("mysourcenum",0);
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
        ca.put("allow", 0);
        ca.put("mysource",0);
        ca.put("mysourcenum",0);
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
        saudi.put("allow", 0);
        saudi.put("mysource",0);
        saudi.put("mysourcenum",0);
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
        sa.put("allow", 0);
        sa.put("mysource",0);
        sa.put("mysourcenum",0);

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


  testtext.setText("내가 선택할 나라는?");

//실시간 데이터 업데이트 감지하기
        final DocumentReference docRef1 = db.collection("나라선택여부").document("selectednation");
        docRef1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {

                    Object n = snapshot.getData().get("nation6").toString();
                    testtext.setText("기록된 이름: "+n);
                    Log.d(TAG, "Current data: " + snapshot.getData());
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
                        select("nation1", name, "대한민국");


 //                       Toast.makeText(getApplication(), "첫번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nationbtn2:
                        select("nation2", name, "중국");



 //                       Toast.makeText(getApplication(), "두번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nationbtn3:
                        select("nation3", name, "호주");
 //                       Toast.makeText(getApplication(), "세번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nationbtn4:
                        select("nation4", name, "캐나다");
 //                       Toast.makeText(getApplication(), "네번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nationbtn5:
                        select("nation5", name, "사우디아라비아");
//                        Toast.makeText(getApplication(), "다섯번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nationbtn6:
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
        db.collection("나라선택여부").document("selectednation")
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


                                db.collection("나라선택여부").document("selectednation")
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

                            }else{
                                Log.d(TAG,"이미 선택된 버튼임 - 선택자:  "+s);
                                if(s.equals(intentname)){
                                    //액티비티 1번 나라로 넘기기
                                    nationstate(nationname);
                                    Toast.makeText(getApplication(), nationnum+" 국가로 이동.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Log.d(TAG,"선택자  "+s);
                                    Toast.makeText(getApplication(), "이미 선택된 나라입니다.", Toast.LENGTH_SHORT).show();
                                }


                            }

                        }else{
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
        startActivity(intent);
    }



    public void onBackButtonClicked(View v){
        finish();
    }
}
