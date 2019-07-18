package com.social.trade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class nation extends AppCompatActivity {

    private TextView name;
    private ImageView nationmark;
    private TextView nowlv, nowoil, nowfe, nowgold, nowwood, nowman, nowmoney;
    private FirebaseFirestore db;
    private String TAG = "activity_nation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_nation);

        Intent intent = getIntent();
        final String nationname = intent.getStringExtra("nationname");

        name = (TextView) findViewById(R.id.Textnation);
        nationmark = (ImageView) findViewById(R.id.nationmark);
        nowlv = (TextView) findViewById(R.id.lv);
        nowoil = (TextView) findViewById(R.id.oil);
        nowfe = (TextView) findViewById(R.id.fe);
        nowgold = (TextView) findViewById(R.id.gold);
        nowwood = (TextView) findViewById(R.id.wood);
        nowman = (TextView) findViewById(R.id.man);
        nowmoney = (TextView) findViewById(R.id.money);

        switch (nationname){
            case "대한민국":
                nationmark.setImageResource(R.drawable.kor);
                name.setText(nationname);
                getsource(nationname);
                break;
            case "중국":
                nationmark.setImageResource(R.drawable.cha);
                name.setText(nationname);
                getsource(nationname);
                break;
            case "호주":
                nationmark.setImageResource(R.drawable.os);
                name.setText(nationname);
                getsource(nationname);
                break;
            case "캐나다":
                nationmark.setImageResource(R.drawable.ca);
                name.setText(nationname);
                getsource(nationname);
                break;
            case "사우디아라비아":
                nationmark.setImageResource(R.drawable.saudi);
                name.setText(nationname);
                getsource(nationname);
                break;
            case "남아프리카공화국":
                nationmark.setImageResource(R.drawable.sa);
                name.setText(nationname);
                getsource(nationname);
                break;
        }
    }

    private void getsource(String name){
        //파이어스토어에서 자료 가져오기
        db = FirebaseFirestore.getInstance();
        db.collection("나라선택여부").document(name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            Object nationlv = document.getData().get("lv").toString() ;
                            Object nationoil = document.getData().get("oil").toString() ;
                            Object nationfe = document.getData().get("fe").toString() ;
                            Object nationgold = document.getData().get("gold").toString() ;
                            Object nationwood = document.getData().get("wood").toString() ;
                            Object nationman = document.getData().get("man").toString() ;
                            Object nationmoney = document.getData().get("money").toString() ;


                            nowlv.setText(""+nationlv);
                            nowoil.setText(""+nationoil);
                            nowfe.setText(""+nationfe);
                            nowgold.setText(""+nationgold);
                            nowwood.setText(""+nationwood);
                            nowman.setText(""+nationman);
                            nowmoney.setText(""+nationmoney);





                        }else{
                            Log.d(TAG, "가져오기 실패", task.getException());
                            //   Toast.makeText(getApplication(), "이미 선택된 나라입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
