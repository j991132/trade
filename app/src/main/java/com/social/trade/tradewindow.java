package com.social.trade;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class tradewindow extends AppCompatActivity {

    private TextView requestnationname, targetnationname, tradewindowmynum, yournum;
    private ImageView requestnationmark, targetnationmark, mysource, yoursource, imgmoney, imgoil, imgfe, imggold, imgwood, imgman;
    private TextView nowlv, nowoil, nowfe, nowgold, nowwood, nowman, nowmoney;
    private FirebaseFirestore db;
    private String TAG = "activity_tradewindow";
    private int presentsource, wantsource, yoursourcenum, yourwantsourcenum;
    private Object yoursname, yoursnum;
    private String mya, youra, mysourcename, requestnation, targetnation;
    private String yoursourcename = "0";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//액티비티 가로모드 고정
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//타이틀바 상태바 없이 전체화면쓰기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_tradewindow);
        MySoundPlayer.initSounds(getApplicationContext());

//인텐트 된 값 저장하기
        Intent intent = getIntent();
        requestnation = intent.getStringExtra("requestnation");
        targetnation = intent.getStringExtra("targetnation");

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
        yoursource = (ImageView) findViewById(R.id.yoursource);
        tradewindowmynum = (TextView) findViewById(R.id.tradewindowmynum);
        yournum = (TextView) findViewById(R.id.yournum);
        imgmoney = (ImageView) findViewById(R.id.imgmoney);
        imgoil = (ImageView) findViewById(R.id.imgoil);
        imgfe = (ImageView) findViewById(R.id.imgfe);
        imggold = (ImageView) findViewById(R.id.imggold);
        imgwood = (ImageView) findViewById(R.id.imgwood);
        imgman = (ImageView) findViewById(R.id.imgman);


//국가 자료 표시하기
        switch (requestnation) {
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

        switch (targetnation) {
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
//버튼 액션
        Button resetbtn = (Button) findViewById(R.id.resetbtn);
        Button tradeokbtn = (Button) findViewById(R.id.tradeokbtn);

        View.OnClickListener Listener2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.resetbtn:
                        MySoundPlayer.play(MySoundPlayer.diring);
                        mysource.setImageResource(R.drawable.ic_launcher_background);
                        tradewindowmynum.setText("0");
                        break;
                    case R.id.tradeokbtn:
//거래수락 버튼 클릭시 액션
//db 거래수락 업데이트
                        db.collection("나라선택여부").document(requestnation)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            Object presentallow = document.getData().get("myallow2").toString();
//                            testtext.setText("기록된 이름: "+s);

                                            if (presentallow.equals("0")) {
                                                MySoundPlayer.play(MySoundPlayer.diring);

                                                Log.d(TAG, "기록이 성공함" + presentallow);
//나는 거래수락 눌렀다고 db업데이트
                                                dbupdate(requestnation, "myallow2", "1");
                                                dbupdate(targetnation, "yourallow", "1");
                                                loading();

//

                                            } else if (presentallow.equals("1")) {
                                                MySoundPlayer.play(MySoundPlayer.diring);

                                                //거래수락창에 이미 yes 상태일때
                                                Log.d(TAG, "현재 거래수락여부는 yes 상태입니다 - 선택자:  " + presentallow);
                                                Toast.makeText(getApplication(), "현재 거래수락여부는 yes 상태입니다.   " + presentallow, Toast.LENGTH_SHORT).show();
                                            }


                                        } else {
                                            MySoundPlayer.play(MySoundPlayer.b);

                                            Log.d(TAG, "가져오기 실패", task.getException());
                                            Toast.makeText(getApplication(), "거래수락여부를 가져올수 없습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        getmyallow(requestnation);


/*
//현재 거래하려는 자료 변수만들기
                    switch (yoursource.getId()){
                        case R.id.money:
                            yoursourcename = "money";
                            break;
                        case R.id.oil:
                            yoursourcename = "oil";
                            break;
                        case R.id.fe:
                            yoursourcename = "fe";
                            break;
                        case R.id.gold:
                            yoursourcename = "gold";
                            break;
                        case R.id.wood:
                            yoursourcename = "wood";
                            break;
                        case R.id.man:
                            yoursourcename = "man";
                            break;
                    }
*/
                        break; //거래수락버튼 종료
                }
            }
        };
        resetbtn.setOnClickListener(Listener2);
        tradeokbtn.setOnClickListener(Listener2);

//내 거래자원 이미지 누를때
        mysource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySoundPlayer.play(MySoundPlayer.diring);

//다이얼로그생성
                final Dialog selectsource = new Dialog(tradewindow.this);
                selectsource.setContentView(R.layout.selectsource);

                TextView meg = (TextView) selectsource.findViewById(R.id.selectsourcetext);
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
                                MySoundPlayer.play(MySoundPlayer.diring);


                                mysource.setImageResource(R.drawable.money);
                                presentsource = Integer.parseInt(nowmoney.getText().toString());
                                mysourcename = "money";
                                sourcedialog("money");
                                selectsource.dismiss();

                                break;
                            case R.id.oilbtn:
                                MySoundPlayer.play(MySoundPlayer.diring);


                                mysource.setImageResource(R.drawable.oil);
                                presentsource = Integer.parseInt(nowoil.getText().toString());
                                mysourcename = "oil";
                                sourcedialog("oil");
                                selectsource.dismiss();

                                break;
                            case R.id.febtn:
                                MySoundPlayer.play(MySoundPlayer.diring);

                                mysource.setImageResource(R.drawable.fe);
                                presentsource = Integer.parseInt(nowfe.getText().toString());
                                mysourcename = "fe";
                                sourcedialog("fe");
                                selectsource.dismiss();

                                break;
                            case R.id.goldbtn:
                                MySoundPlayer.play(MySoundPlayer.diring);

                                mysource.setImageResource(R.drawable.gold);
                                presentsource = Integer.parseInt(nowgold.getText().toString());
                                mysourcename = "gold";
                                sourcedialog("gold");
                                selectsource.dismiss();

                                break;
                            case R.id.woodbtn:
                                MySoundPlayer.play(MySoundPlayer.diring);

                                mysource.setImageResource(R.drawable.wood);
                                presentsource = Integer.parseInt(nowwood.getText().toString());
                                mysourcename = "wood";
                                sourcedialog("wood");
                                selectsource.dismiss();

                                break;
                            case R.id.manbtn:
                                MySoundPlayer.play(MySoundPlayer.diring);

                                mysource.setImageResource(R.drawable.man);
                                presentsource = Integer.parseInt(nowman.getText().toString());
                                mysourcename = "man";
                                sourcedialog("man");
                                selectsource.dismiss();

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
                        MySoundPlayer.play(MySoundPlayer.diring);

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
                        MySoundPlayer.play(MySoundPlayer.diring);

                        mysource.setImageResource(R.drawable.money);
                        presentsource = Integer.parseInt(nowmoney.getText().toString());
                        mysourcename = "money";
                        sourcedialog("money");
                        break;
                    case R.id.imgoil:
                        MySoundPlayer.play(MySoundPlayer.diring);

                        mysource.setImageResource(R.drawable.oil);
                        presentsource = Integer.parseInt(nowoil.getText().toString());
                        mysourcename = "oil";
                        sourcedialog("oil");
                        break;
                    case R.id.imgfe:
                        MySoundPlayer.play(MySoundPlayer.diring);

                        mysource.setImageResource(R.drawable.fe);
                        presentsource = Integer.parseInt(nowfe.getText().toString());
                        mysourcename = "fe";
                        sourcedialog("fe");
                        break;
                    case R.id.imggold:
                        MySoundPlayer.play(MySoundPlayer.diring);

                        mysource.setImageResource(R.drawable.gold);
                        presentsource = Integer.parseInt(nowgold.getText().toString());
                        mysourcename = "gold";
                        sourcedialog("gold");
                        break;
                    case R.id.imgwood:
                        MySoundPlayer.play(MySoundPlayer.diring);

                        mysource.setImageResource(R.drawable.wood);
                        presentsource = Integer.parseInt(nowwood.getText().toString());
                        mysourcename = "wood";
                        sourcedialog("wood");
                        break;
                    case R.id.imgman:
                        MySoundPlayer.play(MySoundPlayer.diring);

                        mysource.setImageResource(R.drawable.man);
                        presentsource = Integer.parseInt(nowman.getText().toString());
                        mysourcename = "man";
                        sourcedialog("man");
                        break;
                }
                Log.d(TAG, "내 자원 이름   " + mysourcename);
            }

        };
        imgmoney.setOnClickListener(Listener);
        imgoil.setOnClickListener(Listener);
        imgfe.setOnClickListener(Listener);
        imggold.setOnClickListener(Listener);
        imgwood.setOnClickListener(Listener);
        imgman.setOnClickListener(Listener);

// 실시간 나의 데이터 감지
        final DocumentReference docRef = db.collection("나라선택여부").document(requestnation);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
// 실시간 데이터변화 감지시 실행
                    getmyallow(requestnation);
                    getsource(requestnation);
/*
                    //무역창에 상대국 자료 보이기
                    switch (yoursourcename){
                        case "money":
                            yoursource.setImageResource(R.drawable.money);
                            yournum.setText(String.valueOf(yoursourcenum));
                            break;
                        case "oil":
                            yoursource.setImageResource(R.drawable.oil);
                            yournum.setText(String.valueOf(yoursourcenum));
                            break;
                        case "fe":
                            yoursource.setImageResource(R.drawable.fe);
                            yournum.setText(String.valueOf(yoursourcenum));
                            break;
                        case "gold":
                            yoursource.setImageResource(R.drawable.gold);
                            yournum.setText(String.valueOf(yoursourcenum));
                            break;
                        case "wood":
                            yoursource.setImageResource(R.drawable.wood);
                            yournum.setText(String.valueOf(yoursourcenum));
                            break;
                        case "man":
                            yoursource.setImageResource(R.drawable.man);
                            yournum.setText(String.valueOf(yoursourcenum));
                            break;
                        case "0":
                            break;
                    }


                    if(mya=="yes"&& youra=="yes"){
//db에 업데이트
                        dbupdate(requestnation, mysourcename, String.valueOf(presentsource-wantsource));
                        dbupdate(requestnation, yoursourcename, String.valueOf(yourwantsourcenum+yoursourcenum));
                        dbupdate(requestnation,"myallow","0");
                        dbupdate(requestnation,"yourallow","0");
                    }
 */
                    Log.d(TAG, "Current data: " + snapshot.getData());
                    //  Toast.makeText(getApplication(), "Current data: " + snapshot.getData(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });

    }  //본문 끝

    //내 자원 이미지 누를때 다이얼로그 불러오기
    private void sourcedialog(String sourcename) {
        //다이얼로그생성
        final Dialog sourceconfirm = new Dialog(tradewindow.this);
        sourceconfirm.setContentView(R.layout.tradesource);

        ImageView mysourceImage = (ImageView) sourceconfirm.findViewById(R.id.tradesourceimage);
        final EditText mysourcenum = (EditText) sourceconfirm.findViewById(R.id.tradesourcenum);
        Button selectokbtn = (Button) sourceconfirm.findViewById(R.id.tradesourceok);
        Button selectcanclebtn = (Button) sourceconfirm.findViewById(R.id.cancel);

//내가 선택한 자원 이미지 보이기
        switch (sourcename) {
            case "money":
                mysourceImage.setImageResource(R.drawable.money);
            break;
            case "oil":
                mysourceImage.setImageResource(R.drawable.oil);

                break;
            case "fe":
                mysourceImage.setImageResource(R.drawable.fe);

                break;
            case "gold":
                mysourceImage.setImageResource(R.drawable.gold);

                break;
            case "wood":
                mysourceImage.setImageResource(R.drawable.wood);

            case "man":
                mysourceImage.setImageResource(R.drawable.man);

                break;
            case "0":
                break;
        }

//확인버튼
        selectokbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 각 자원별 수량이 입력 수량보다 넘지 않도록 비교하는 부분 필요
                wantsource = Integer.parseInt(mysourcenum.getText().toString());
                if (presentsource >= wantsource) {
                    MySoundPlayer.play(MySoundPlayer.diring);

                    tradewindowmynum.setText(mysourcenum.getText().toString());
//db에 나의 자원 업데이트
                    //내 자원종류 db 업데이트
//                    dbupdate(requestnation,"mysource",mysourcename);
//내 자원수량 db업데이트
//                    dbupdate(requestnation,"mysourcenum", String.valueOf(wantsource));
//내 거래자원 명 상대방 db에 업데이트
//                    dbupdate(targetnation,"yoursource",mysourcename);
//내 거래자원 량 상대방 db에 업데이트
//                    dbupdate(targetnation,"yoursourcenum", String.valueOf(wantsource));
                    dbupdate2(targetnation, "yoursource", mysourcename, "yoursourcenum", String.valueOf(wantsource));
                    sourceconfirm.dismiss();
                } else {
                    MySoundPlayer.play(MySoundPlayer.b);

                    Log.w(TAG, "" + presentsource + "   " + wantsource);
                    Toast.makeText(getApplication(), "가지고 있는 수량이 모자랍니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
//취소버튼
        selectcanclebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.diring);

                mysource.setImageResource(R.drawable.ic_launcher_background);
                tradewindowmynum.setText("0");
                dbupdate2(targetnation, "yoursource", "0", "yoursourcenum", "0");
                sourceconfirm.dismiss();
            }
        });

        sourceconfirm.show();
    }

    //파이어스토어에서 자료 가져오기
    private void getsource(String name) {

        db = FirebaseFirestore.getInstance();
        db.collection("나라선택여부").document(name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Object nationlv = document.getData().get("lv").toString();
                            Object nationoil = document.getData().get("oil").toString();
                            Object nationfe = document.getData().get("fe").toString();
                            Object nationgold = document.getData().get("gold").toString();
                            Object nationwood = document.getData().get("wood").toString();
                            Object nationman = document.getData().get("man").toString();
                            Object nationmoney = document.getData().get("money").toString();


                            //                           nowlv.setText(""+nationlv);
                            nowoil.setText(nationoil.toString());
                            nowfe.setText(nationfe.toString());
                            nowgold.setText(nationgold.toString());
                            nowwood.setText(nationwood.toString());
                            nowman.setText(nationman.toString());
                            nowmoney.setText(nationmoney.toString());


                        } else {
                            Log.d(TAG, "가져오기 실패", task.getException());
                            //   Toast.makeText(getApplication(), "이미 선택된 나라입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //프로그레스 다이얼로그
    public void loading() {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = new ProgressDialog(tradewindow.this);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("상대국가의 거래 수락을 기다리는 중입니다...");
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        } else {
                            progressDialog.show();
                        }

                    }
                }, 0);
    }

    public void loadingEnd() {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            progressDialog.dismiss();
                        } catch (Exception e) {
                        }

                    }
                }, 0
        );
    }

    //거래 수락시 나의 db에서 allow 가져오기
    private void getmyallow(String name) {

        db = FirebaseFirestore.getInstance();
        db.collection("나라선택여부").document(name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Object myallow = document.getData().get("myallow2").toString();
                            Object yourallow = document.getData().get("yourallow").toString();

                            yoursname = document.getData().get("yoursource").toString();
                            yoursnum = document.getData().get("yoursourcenum").toString();
                            int a = Integer.parseInt(myallow.toString());
                            int b = Integer.parseInt(yourallow.toString());
                            mya = myallow.toString();
                            youra = yourallow.toString();
                            Log.d(TAG, "a" + a + "b" + b);


                            yoursourcename = yoursname.toString();
                            yoursourcenum = Integer.parseInt(yoursnum.toString());
                            Object yourwant = document.getData().get(yoursourcename).toString();
                            yourwantsourcenum = Integer.parseInt(yourwant.toString());

                            if (a == 1 && b == 1) {
                                loadingEnd();
                                MySoundPlayer.play(MySoundPlayer.tradeok);

                                Log.d(TAG, "이후   " + mya + youra + a + b);
//db에 업데이트
                                Log.d(TAG, "에러   " + mysourcename + "   " + presentsource + "    " + wantsource);
                                dbupdate8(requestnation, mysourcename, String.valueOf(presentsource - wantsource), yoursourcename, String.valueOf(yourwantsourcenum + yoursourcenum), "myallow2", "0", "yourallow", "0", "yoursource", "0", "yoursourcenum", "0", "request", "0", "myallow", "0");
//                                dbupdate2(requestnation, mysourcename, String.valueOf(presentsource-wantsource), yoursourcename, String.valueOf(yourwantsourcenum+yoursourcenum));
//                                dbupdate2(requestnation,"myallow2","0", "yourallow","0");
//                                dbupdate2(requestnation, "yoursource", "0", "yoursourcenum", "0");
//                                dbupdate2(requestnation, "request", "0", "myallow", "0");
                                Toast.makeText(getApplication(), "무역이 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
//무역창에 상대국 자료 보이기
                            switch (yoursourcename) {
                                case "money":
                                    yoursource.setImageResource(R.drawable.money);
                                    yournum.setText(String.valueOf(yoursourcenum));
                                    break;
                                case "oil":
                                    yoursource.setImageResource(R.drawable.oil);
                                    yournum.setText(String.valueOf(yoursourcenum));
                                    break;
                                case "fe":
                                    yoursource.setImageResource(R.drawable.fe);
                                    yournum.setText(String.valueOf(yoursourcenum));
                                    break;
                                case "gold":
                                    yoursource.setImageResource(R.drawable.gold);
                                    yournum.setText(String.valueOf(yoursourcenum));
                                    break;
                                case "wood":
                                    yoursource.setImageResource(R.drawable.wood);
                                    yournum.setText(String.valueOf(yoursourcenum));
                                    break;
                                case "man":
                                    yoursource.setImageResource(R.drawable.man);
                                    yournum.setText(String.valueOf(yoursourcenum));
                                    break;
                                case "0":
                                    break;
                            }
                            Log.d(TAG, "이전   " + mya + youra);


                        } else {
                            Log.d(TAG, "가져오기 실패", task.getException());
                            //   Toast.makeText(getApplication(), "이미 선택된 나라입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //db업데이트
    private void dbupdate(String name, String field, String data) {
        db.collection("나라선택여부").document(name)
                .update(field, data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Log.d(TAG, "필드 업데이트 성공함");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "쓰기 실패", e);
                    }
                });
    }

    //db 2개 데이터 업데이트
    private void dbupdate2(String name, String field1, String data1, String field2, String data2) {
        db.collection("나라선택여부").document(name)
                .update(field1, data1, field2, data2)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Log.d(TAG, "필드 업데이트 성공함");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "쓰기 실패", e);
                    }
                });
    }

    //db 8개 업데이트
    private void dbupdate8(String name, String field1, String data1, String field2, String data2, String field3, String data3, String field4, String data4, String field5, String data5, String field6, String data6, String field7, String data7, String field8, String data8) {
        db.collection("나라선택여부").document(name)
                .update(field1, data1, field2, data2, field3, data3, field4, data4, field5, data5, field6, data6, field7, data7, field8, data8)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Log.d(TAG, "필드 업데이트 성공함");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "쓰기 실패", e);
                    }
                });
    }
}
