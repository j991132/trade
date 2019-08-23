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
                tradetargetnation.setContentView( R.layout.tradetargetnation );

//setcontentview 를 먼저 연결해준 뒤에 텍스트뷰를 선언해야 널 에러가 안뜬다
                TextView message = (TextView) tradetargetnation.findViewById(R.id.title);
                message.setText("무역하고 싶은 나라는?");

                Button nation1 = (Button) tradetargetnation.findViewById(R.id.nation1);
                Button nation2 = (Button) tradetargetnation.findViewById(R.id.nation2);
                Button nation3 = (Button) tradetargetnation.findViewById(R.id.nation3);
                Button nation4 = (Button) tradetargetnation.findViewById(R.id.nation4);
                Button nation5 = (Button) tradetargetnation.findViewById(R.id.nation5);
                Button nation6 = (Button) tradetargetnation.findViewById(R.id.nation6);
//내가 선택한 나라 제외하고 버튼 띄우기
                switch (nationname){
                    case "대한민국":
                        nation1.setVisibility(View.INVISIBLE);
                        break;
                    case "중국":
                        nation2.setVisibility(View.INVISIBLE);
                        break;
                    case "호주":
                        nation3.setVisibility(View.INVISIBLE);
                        break;
                    case "캐나다":
                        nation4.setVisibility(View.INVISIBLE);
                        break;
                    case "사우디아라비아":
                        nation5.setVisibility(View.INVISIBLE);
                        break;
                    case "남아프리카공화국":
                        nation6.setVisibility(View.INVISIBLE);
                        break;
                }
//다이얼로그 버튼액션
                View.OnClickListener Listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.nation1:



                                //       Toast.makeText(getApplication(), "첫번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nation2:
                                tradeconfirm(nationname,"중국");



                                //                       Toast.makeText(getApplication(), "두번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nation3:

                                //                       Toast.makeText(getApplication(), "세번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nation4:

                                //                       Toast.makeText(getApplication(), "네번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nation5:

//                        Toast.makeText(getApplication(), "다섯번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nation6:

                                //                       Toast.makeText(getApplication(), "여섯번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                };
                nation1.setOnClickListener(Listener);
                nation2.setOnClickListener(Listener);
                nation3.setOnClickListener(Listener);
                nation4.setOnClickListener(Listener);
                nation5.setOnClickListener(Listener);
                nation6.setOnClickListener(Listener);


                Button tradecanale = (Button) tradetargetnation.findViewById(R.id.tradecancle);
                tradecanale.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tradetargetnation.dismiss();
                    }
                });
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
//다이얼로그 나라 선택 확인
    public void tradeconfirm(final String nationname, final String targetnation){
        //다이얼로그생성
        final Dialog tradeok = new Dialog( this );
        tradeok.setContentView( R.layout.confirmdialog);

        TextView  meg = (TextView) tradeok.findViewById(R.id.confirmtitle);
        meg.setText(targetnation+"   과 무역할까요?");

        Button okbtn = (Button) tradeok.findViewById(R.id.ok);
        Button canclebtn = (Button) tradeok.findViewById(R.id.cancel);
//다이얼로그 ok버튼 클릭액션
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                traderequest(nationname, targetnation);
                tradeok.dismiss();
//ondismissListener 사용을 해보자                tradetargetnation.dismiss();
            }
        });

//다이얼로그 취소버튼 클릭액션
        canclebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tradeok.dismiss();
            }
        });

        tradeok.show();
    }
//다이얼로그 나라선택 확인버튼 클릭시 요청국가 파이어스토어 업데이트
    public void traderequest(final String requestnation, final String targetnation) {
        db.collection("나라선택여부").document(targetnation)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            Object requeststate = document.getData().get("request").toString();

                            if (requeststate.equals("0")){
//                                Log.d(TAG, "기록이 성공함"+requeststate);


                                db.collection("나라선택여부").document(targetnation)
                                        .update("request", requestnation)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
//무역창 띄우기 인서트
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
                                Log.d(TAG,"현재 다른 나라와 무역중임 - "+targetnation+" 과 무역중인 나라:  "+requestnation);
                                if(requeststate.equals(requestnation)){
                                    //이미 나와 거래중인데 무역창이 실수로 꺼질경우 대비
//무역창 띄우기 인서트
                                    Toast.makeText(getApplication(), "현재 우리나라와 무역중이었음. 무역창으로 다시 이동합니다." , Toast.LENGTH_SHORT).show();
                                }else{
                                    Log.d(TAG,"현재 다른 나라와 무역중임 - "+targetnation+" 과 무역중인 나라:  "+requestnation);
                                    Toast.makeText(getApplication(), "현재 다른 나라와 무역중임 - "+targetnation+" 과 무역중인 나라:  "+requestnation, Toast.LENGTH_SHORT).show();
                                }


                            }

                        }else{
                            Log.d(TAG, "가져오기 실패", task.getException());
                            Toast.makeText(getApplication(), "자료 업데이트 실패.", Toast.LENGTH_SHORT).show();
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