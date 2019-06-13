package com.social.trade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class tradegame extends AppCompatActivity {

    private FirebaseFirestore db;
    private String TAG = "activity_tradegame";

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

        Intent intent = getIntent();
        final String name = intent.getStringExtra("ename");

        final Map<String, Object> selectednation = new HashMap<>();
        selectednation.put("nation1", 0);
        selectednation.put("nation2", 0);
        selectednation.put("nation3", 0);
        selectednation.put("nation4", 0);
        selectednation.put("nation5", 0);
        selectednation.put("nation6", 0);


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
                    }
                }else{
                    Log.d(TAG, "가져오기 실패", task.getException());
                }
            }
        });




        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.nationbtn1:
                        select("nation1", name);
/*
                        db.collection("나라선택여부").document("selectednation")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()){
                                    DocumentSnapshot document = task.getResult();
                                    Object s = document.getData().get("nation1").toString();


                                  if (s.equals("0")){
                                      Log.d(TAG, "기록이 성공함"+s);


                                      db.collection("나라선택여부").document("selectednation")
                                              .update("nation1", name)
                                              .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                  @Override
                                                  public void onSuccess(Void aVoid) {
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
                                        Log.d(TAG,"이미 버튼이 눌림"+s);
                                        if(s.equals(name)){
                                            //액티비티 1번 나라로 넘기기
                                            Toast.makeText(getApplication(), "1번 국가로 이동.", Toast.LENGTH_SHORT).show();
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

*/



 //                       Toast.makeText(getApplication(), "첫번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nationbtn2:
                        select("nation2", name);
 //                       Toast.makeText(getApplication(), "두번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nationbtn3:
                        select("nation3", name);
 //                       Toast.makeText(getApplication(), "세번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nationbtn4:
                        select("nation4", name);
 //                       Toast.makeText(getApplication(), "네번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nationbtn5:
                        select("nation5", name);
//                        Toast.makeText(getApplication(), "다섯번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nationbtn6:
                        select("nation6", name);
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

    public void select(final String nationnum, final String intentname) {
        db.collection("나라선택여부").document("selectednation")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            Object s = document.getData().get(nationnum).toString();


                            if (s.equals("0")){
                                Log.d(TAG, "기록이 성공함"+s);


                                db.collection("나라선택여부").document("selectednation")
                                        .update(nationnum, intentname)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
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

    public void onBackButtonClicked(View v){
        finish();
    }
}
