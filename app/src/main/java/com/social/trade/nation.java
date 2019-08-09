package com.social.trade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private TextView nowlv, nowoil, nowfe, nowgold, nowwood, nowman, nowmoney, goil, gfe, ggold, gwood, gman;
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
        goil = (TextView) findViewById(R.id.goil);
        gfe = (TextView) findViewById(R.id.gfe);
        ggold = (TextView) findViewById(R.id.ggold);
        gwood = (TextView) findViewById(R.id.gwood);
        gman = (TextView) findViewById(R.id.gman);


        switch (nationname){
            case "대한민국":
                nationmark.setImageResource(R.drawable.kor);
                name.setText(nationname);
                getsource(nationname);
                goalsource(nationname);
                break;
            case "중국":
                nationmark.setImageResource(R.drawable.cha);
                name.setText(nationname);
                getsource(nationname);
                goalsource(nationname);
                break;
            case "호주":
                nationmark.setImageResource(R.drawable.os);
                name.setText(nationname);
                getsource(nationname);
                goalsource(nationname);
                break;
            case "캐나다":
                nationmark.setImageResource(R.drawable.ca);
                name.setText(nationname);
                getsource(nationname);
                goalsource(nationname);
                break;
            case "사우디아라비아":
                nationmark.setImageResource(R.drawable.saudi);
                name.setText(nationname);
                getsource(nationname);
                goalsource(nationname);
                break;
            case "남아프리카공화국":
                nationmark.setImageResource(R.drawable.sa);
                name.setText(nationname);
                getsource(nationname);
                goalsource(nationname);
                break;
        }

//버튼액션
//무역대상국가 버튼
        Button tradetartgetbtn = (Button) findViewById(R.id.tradetargetbtn);
        tradetartgetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//다이얼로그생성
                final Dialog tradetargetnation = new Dialog( nation.this );
                tradetargetnation.setTitle( "어느 나라와 무역할까요?" );
                tradetargetnation.setContentView( R.layout.tradetargetnation );

                tradetargetnation.show();

            }
        });

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

    private void goalsource(String nationname){
        //레벨에 따라 목적자원량 셋팅하기
        db = FirebaseFirestore.getInstance();
        db.collection("나라선택여부").document(nationname)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            Object nationlv = document.getData().get("lv").toString() ;
                            String nlv = (String) nationlv;
                            switch (nlv) {
                                case "0" :
                                    goil.setText("0");
                                    gfe.setText("0");
                                    ggold.setText("0");
                                    gwood.setText("6");
                                    gman.setText("6");
                                 break;

                                case "1" :
                                    goil.setText("4");
                                    gfe.setText("0");
                                    ggold.setText("0");
                                    gwood.setText("6");
                                    gman.setText("14");
                                    break;
                                case "2" :
                                    goil.setText("11");
                                    gfe.setText("0");
                                    ggold.setText("1");
                                    gwood.setText("7");
                                    gman.setText("17");
                                    break;
                                case "3" :
                                    goil.setText("11");
                                    gfe.setText("8");
                                    ggold.setText("2");
                                    gwood.setText("7");
                                    gman.setText("20");
                                    break;
                                case "4" :
                                    goil.setText("13");
                                    gfe.setText("13");
                                    ggold.setText("4");
                                    gwood.setText("7");
                                    gman.setText("23");
                                    break;
                                case "5" :
                                    goil.setText("14");
                                    gfe.setText("17");
                                    ggold.setText("9");
                                    gwood.setText("7");
                                    gman.setText("25");
                                    break;
                                case "6" :
                                    goil.setText("15");
                                    gfe.setText("20");
                                    ggold.setText("15");
                                    gwood.setText("7");
                                    gman.setText("27");
                                    break;
                                case "7" :
                                    goil.setText("18");
                                    gfe.setText("25");
                                    ggold.setText("17");
                                    gwood.setText("9");
                                    gman.setText("30");
                                    break;
                            }









                        }else{
                            Log.d(TAG, "가져오기 실패", task.getException());
                            //   Toast.makeText(getApplication(), "이미 선택된 나라입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

/*
//다이얼로그생성
				final Dialog reset = new Dialog( context );
				reset.setTitle( "초기화 할까요?" );
				reset.setContentView( R.layout.delete );
				Button delBtn = ( Button ) reset.findViewById( R.id.delete );
				Button cancelBtn = ( Button ) reset.findViewById( R.id.cancel );
				// 다이얼로그 삭제버튼 누를 때

				delBtn.setOnClickListener( new View.OnClickListener()
				{
					@Override
					public void onClick( View view )
					{
						SharedPreferences pref = getSharedPreferences( "SaveState", MODE_PRIVATE );
						pref.edit().clear().commit();
						count = 0;
						restoreFromSavedState();
						layout.removeAllViews();
						reset.dismiss();
					}
				} );
				//다이얼로그 취소버튼 누를 때
				cancelBtn.setOnClickListener( new View.OnClickListener()
				{
					@Override
					public void onClick( View view )
					{
						reset.dismiss();
					}
				} );
				reset.show();
				//return true;
			}

 */