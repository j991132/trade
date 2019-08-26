package com.social.trade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class tradewindow extends AppCompatActivity {

    private TextView requestnationname, targetnationname;
    private ImageView requestnationmark, targetnationmark;
    private TextView nowlv, nowoil, nowfe, nowgold, nowwood, nowman, nowmoney;
    private FirebaseFirestore db;
    private String TAG = "activity_tradewindow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//액티비티 가로모드 고정
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_tradewindow);

        Intent intent = getIntent();
        final String requestnation = intent.getStringExtra("requestnation");
        final String targetnation = intent.getStringExtra("targetnation");

        requestnationname = (TextView) findViewById(R.id.mynationname);
        requestnationmark = (ImageView) findViewById(R.id.mynationmark);
        targetnationname = (TextView) findViewById(R.id.targetnationname);
        targetnationmark = (ImageView) findViewById(R.id.targetnationmark);
        nowlv = (TextView) findViewById(R.id.lv);
        nowoil = (TextView) findViewById(R.id.oil);
        nowfe = (TextView) findViewById(R.id.fe);
        nowgold = (TextView) findViewById(R.id.gold);
        nowwood = (TextView) findViewById(R.id.wood);
        nowman = (TextView) findViewById(R.id.man);
        nowmoney = (TextView) findViewById(R.id.money);

        switch (requestnation){
            case "대한민국":
                requestnationmark.setImageResource(R.drawable.kor);
                requestnationname.setText(requestnation);

                getsource(requestnation);

                break;
            case "중국":
                requestnationmark.setImageResource(R.drawable.cha);
                requestnationname.setText(requestnation);
                getsource(requestnation);
                break;
            case "호주":
                requestnationmark.setImageResource(R.drawable.os);
                requestnationname.setText(requestnation);
                getsource(requestnation);
                break;
            case "캐나다":
                requestnationmark.setImageResource(R.drawable.ca);
                requestnationname.setText(requestnation);
                getsource(requestnation);
                break;
            case "사우디아라비아":
                requestnationmark.setImageResource(R.drawable.saudi);
                requestnationname.setText(requestnation);
                getsource(requestnation);
                break;
            case "남아프리카공화국":
                requestnationmark.setImageResource(R.drawable.sa);
                requestnationname.setText(requestnation);
                getsource(requestnation);
                break;
        }

        switch (targetnation){
            case "대한민국":
                targetnationmark.setImageResource(R.drawable.kor);
                targetnationname.setText(targetnation);
                break;
            case "중국":
                targetnationmark.setImageResource(R.drawable.cha);
                targetnationname.setText(requestnation);
                break;
            case "호주":
                targetnationmark.setImageResource(R.drawable.os);
                targetnationname.setText(requestnation);
                break;
            case "캐나다":
                targetnationmark.setImageResource(R.drawable.ca);
                targetnationname.setText(requestnation);
                break;
            case "사우디아라비아":
                targetnationmark.setImageResource(R.drawable.saudi);
                targetnationname.setText(requestnation);
                break;
            case "남아프리카공화국":
                targetnationmark.setImageResource(R.drawable.sa);
                targetnationname.setText(requestnation);
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


 //                           nowlv.setText(""+nationlv);
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
