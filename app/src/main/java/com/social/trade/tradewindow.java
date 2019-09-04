package com.social.trade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class tradewindow extends AppCompatActivity {

    private TextView requestnationname, targetnationname, tradewindowmynum;
    private ImageView requestnationmark, targetnationmark, mysource, imgmoney, imgoil, imgfe, imggold, imgwood, imgman;
    private TextView nowlv, nowoil, nowfe, nowgold, nowwood, nowman, nowmoney;
    private FirebaseFirestore db;
    private String TAG = "activity_tradewindow";
    private int presentsource, wantsource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//액티비티 가로모드 고정
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//타이틀바 상태바 없이 전체화면쓰기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        setContentView(R.layout.activity_tradewindow);
//인텐트 된 값 저장하기
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
        mysource = (ImageView) findViewById(R.id.mysource);
        tradewindowmynum = (TextView) findViewById(R.id.tradewindowmynum);
        imgmoney = (ImageView) findViewById(R.id.imgmoney);
        imgoil = (ImageView) findViewById(R.id.imgoil);
        imgfe = (ImageView) findViewById(R.id.imgfe);
        imggold = (ImageView) findViewById(R.id.imggold);
        imgwood = (ImageView) findViewById(R.id.imgwood);
        imgman = (ImageView) findViewById(R.id.imgman);





//국가 자료 표시하기
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
                targetnationname.setText(targetnation);
                break;
            case "호주":
                targetnationmark.setImageResource(R.drawable.os);
                targetnationname.setText(targetnation);
                break;
            case "캐나다":
                targetnationmark.setImageResource(R.drawable.ca);
                targetnationname.setText(targetnation);
                break;
            case "사우디아라비아":
                targetnationmark.setImageResource(R.drawable.saudi);
                targetnationname.setText(targetnation);
                break;
            case "남아프리카공화국":
                targetnationmark.setImageResource(R.drawable.sa);
                targetnationname.setText(targetnation);
                break;
        }
//내 거래자원 이미지 누를때
        mysource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//다이얼로그생성
                final Dialog selectsource = new Dialog( tradewindow.this );
                selectsource.setContentView( R.layout.selectsource);

                TextView  meg = (TextView) selectsource.findViewById(R.id.selectsourcetext);
                meg.setText("거래할 자원을 선택하세요!");

                Button moneybtn = (Button) selectsource.findViewById(R.id.moneybtn);
                Button oilbtn = (Button) selectsource.findViewById(R.id.oilbtn);
                Button febtn = (Button) selectsource.findViewById(R.id.febtn);
                Button goldbtn = (Button) selectsource.findViewById(R.id.goldbtn);
                Button woodbtn = (Button) selectsource.findViewById(R.id.woodbtn);
                Button manbtn = (Button) selectsource.findViewById(R.id.manbtn);


                Button canclebtn = (Button) selectsource.findViewById(R.id.selectcancle);
//다이얼로그 버튼 클릭액션
                View.OnClickListener Listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.moneybtn:

                                mysource.setImageResource(R.drawable.money);
                                presentsource = Integer.parseInt(nowmoney.getText().toString());
                                sourcedialog("money");
                                selectsource.dismiss();
                                //       Toast.makeText(getApplication(), "첫번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.oilbtn:

                                mysource.setImageResource(R.drawable.oil);
                                presentsource = Integer.parseInt(nowoil.getText().toString());
                                sourcedialog("oil");
                                selectsource.dismiss();
                                //                       Toast.makeText(getApplication(), "두번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.febtn:
                                mysource.setImageResource(R.drawable.fe);
                                presentsource = Integer.parseInt(nowfe.getText().toString());
                                sourcedialog("fe");
                                selectsource.dismiss();
                                //                       Toast.makeText(getApplication(), "세번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.goldbtn:
                                mysource.setImageResource(R.drawable.gold);
                                presentsource = Integer.parseInt(nowgold.getText().toString());
                                sourcedialog("gold");
                                selectsource.dismiss();
                                //                       Toast.makeText(getApplication(), "네번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.woodbtn:
                                mysource.setImageResource(R.drawable.wood);
                                presentsource = Integer.parseInt(nowwood.getText().toString());
                                sourcedialog("wood");
                                selectsource.dismiss();
//                        Toast.makeText(getApplication(), "다섯번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.manbtn:
                                mysource.setImageResource(R.drawable.man);
                                presentsource = Integer.parseInt(nowman.getText().toString());
                                sourcedialog("man");
                                selectsource.dismiss();
                                //                       Toast.makeText(getApplication(), "여섯번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                };
                moneybtn.setOnClickListener(Listener);
                oilbtn.setOnClickListener(Listener);
                febtn.setOnClickListener(Listener);
                goldbtn.setOnClickListener(Listener);
                woodbtn.setOnClickListener(Listener);
                manbtn.setOnClickListener(Listener);


//다이얼로그 취소버튼 클릭액션
                canclebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mysource.setImageResource(R.drawable.ic_launcher_background);
                        tradewindowmynum.setText("0");
                        selectsource.dismiss();
                    }
                });

                selectsource.show();
            }
        });
//내 자원 이미지 누를때 액션
        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.imgmoney:
                        mysource.setImageResource(R.drawable.money);
                        presentsource = Integer.parseInt(nowmoney.getText().toString());

                        sourcedialog("money");
                        break;
                    case R.id.imgoil:
                        mysource.setImageResource(R.drawable.oil);
                        presentsource = Integer.parseInt(nowoil.getText().toString());
                        sourcedialog("oil");
                        break;
                    case R.id.imgfe:
                        mysource.setImageResource(R.drawable.fe);
                        presentsource = Integer.parseInt(nowfe.getText().toString());
                        sourcedialog("fe");
                        break;
                    case R.id.imggold:
                        mysource.setImageResource(R.drawable.gold);
                        presentsource = Integer.parseInt(nowgold.getText().toString());
                        sourcedialog("gold");
                        break;
                    case R.id.imgwood:
                        mysource.setImageResource(R.drawable.wood);
                        presentsource = Integer.parseInt(nowwood.getText().toString());
                        sourcedialog("wood");
                        break;
                    case R.id.imgman:
                        mysource.setImageResource(R.drawable.man);
                        presentsource = Integer.parseInt(nowman.getText().toString());
                        sourcedialog("man");
                        break;
                }
            }
        };
        imgmoney.setOnClickListener(Listener);
        imgoil.setOnClickListener(Listener);
        imgfe.setOnClickListener(Listener);
        imggold.setOnClickListener(Listener);
        imgwood.setOnClickListener(Listener);
        imgman.setOnClickListener(Listener);

    }  //본문 끝

//내 자원 이미지 누를때 다이얼로그 불러오기
    private void sourcedialog(String sourcename){
        //다이얼로그생성
        final Dialog sourceconfirm = new Dialog( tradewindow.this );
        sourceconfirm.setContentView( R.layout.tradesource);

        final EditText mysourcenum = (EditText) sourceconfirm.findViewById(R.id.tradesourcenum);
        Button selectokbtn = (Button) sourceconfirm.findViewById(R.id.tradesourceok);
        Button selectcanclebtn = (Button) sourceconfirm.findViewById(R.id.cancel);


//확인버튼
        selectokbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            // 각 자원별 수량이 입력 수량보다 넘지 않도록 비교하는 부분 필요
                wantsource = Integer.parseInt(mysourcenum.getText().toString());
                if (presentsource >= wantsource) {
                    int a = Integer.parseInt(mysourcenum.getText().toString());
                    tradewindowmynum.setText(mysourcenum.getText().toString());
                    sourceconfirm.dismiss();
                }else{
                    Log.w(TAG, ""+presentsource+"   "+wantsource);
                    Toast.makeText(getApplication(), "가지고 있는 수량이 모자랍니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
//취소버튼
        selectcanclebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mysource.setImageResource(R.drawable.ic_launcher_background);
                tradewindowmynum.setText("0");
                sourceconfirm.dismiss();
            }
        });

        sourceconfirm.show();
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
                            nowoil.setText(nationoil.toString());
                            nowfe.setText(nationfe.toString());
                            nowgold.setText(nationgold.toString());
                            nowwood.setText(nationwood.toString());
                            nowman.setText(nationman.toString());
                            nowmoney.setText(nationmoney.toString());





                        }else{
                            Log.d(TAG, "가져오기 실패", task.getException());
                            //   Toast.makeText(getApplication(), "이미 선택된 나라입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
