package com.social.trade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class tradegame extends AppCompatActivity {

    private FirebaseFirestore db;
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

        final Map<String, Object> selectednation = new HashMap<>();
        selectednation.put("nation1", 0);
        selectednation.put("nation2", 0);
        selectednation.put("nation3", 0);
        selectednation.put("nation4", 0);
        selectednation.put("nation5", 0);
        selectednation.put("nation6", 0);

        db = FirebaseFirestore.getInstance();

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

        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.nationbtn1:

                        Map<String, Object> nation1 = new HashMap<>();
                        nation1.put("nation1", 1);
//                        db = FirebaseFirestore.getInstance();

                        db.collection("나라선택여부").document("selectednation")
                                .set(nation1, SetOptions.merge());
 /*                               .addOnSuccessListener(new OnSuccessListener<Void>() {
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
*/

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
