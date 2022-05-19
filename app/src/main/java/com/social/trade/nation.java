package com.social.trade;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class nation extends AppCompatActivity implements DialogInterface.OnDismissListener {

    private TextView name;
    private ImageView nationmark, imagetech;
    private TextView nowlv, nowoil, nowfe, nowgold, nowwood, nowman, nowmoney, goil, gfe, ggold, gwood, gman;
    private int technum, cost;
    private FirebaseFirestore db;
    private String TAG = "activity_nation", nationname, gameId;
    private DialogInterface.OnDismissListener onDismissListener = null;
    private Dialog tradetargetnation, tradeok;
    private Object myallow, requeststate;
    private ProgressDialog progressDialog;
    private BackkeyHandler backkeyHandler = new BackkeyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_nation);
        MySoundPlayer.initSounds(getApplicationContext());
        Intent intent = getIntent();
        nationname = intent.getStringExtra("nationname");
        gameId = intent.getStringExtra("gameId");

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


        switch (nationname) {
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
            case "선생님나라":
//                nationmark.setImageResource(R.drawable.sa);
                name.setText(nationname);
                getsource(nationname);
                goalsource(nationname);
                break;
        }

//버튼액션

//기술수준 버튼
        imagetech = (ImageView) findViewById(R.id.imagetech);
        imagetech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySoundPlayer.play(MySoundPlayer.diring);
                //다이얼로그생성
                final Dialog techbuy = new Dialog(nation.this);
                techbuy.setContentView(R.layout.confirmdialog);

                TextView meg = (TextView) techbuy.findViewById(R.id.confirmtitle);

                getsource(nationname);
                switch (technum) {
                    case 0:
                        cost = 30;

                        break;
                    case 1:
                        cost = 40;

                        break;
                    case 2:
                        cost = 50;

                        break;
                    case 3:
                        cost = 60;
                        break;
                    case 4:
                        cost = 80;

                        break;
                    case 5:
                        cost = 100;

                        break;
                    case 6:
                        cost = 200;

                        break;
                    case 7:
                        cost = 300;

                        break;
                    case 8:


                        break;
                }

                meg.setText("  기술 개발비용  " + cost + " 원을 지불하고 기술수준을 업그레이드 하시겠습니까?  ");

                Button okbtn = (Button) techbuy.findViewById(R.id.ok);
                Button canclebtn = (Button) techbuy.findViewById(R.id.cancel);


//확인버튼
                okbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // 각 자원별 수량이 입력 수량보다 넘지 않도록 비교하는 부분 필요
                        int mymoney = Integer.parseInt(nowmoney.getText().toString());
                        if (mymoney >= cost) {

                            MySoundPlayer.play(MySoundPlayer.diring);
//db에 나의 자원 업데이트

                            dbupdate2(nationname, "money", String.valueOf(mymoney - cost), "tech", String.valueOf(technum + 1));
//                            dbupdate(nationname, "tech", String.valueOf(technum + 1));

                            techbuy.dismiss();
                        } else {
                            MySoundPlayer.play(MySoundPlayer.b);
                            Log.w(TAG, "mymoney  " + mymoney + "  cost  " + cost);
                            Toast.makeText(getApplication(), "가지고 있는 금액이 모자랍니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//취소버튼
                canclebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        MySoundPlayer.play(MySoundPlayer.diring);
                        techbuy.dismiss();
                    }
                });

                techbuy.show();
                MySoundPlayer.play(MySoundPlayer.confirm);
            }
        });

//무역대상국가 버튼
        Button tradetartgetbtn = (Button) findViewById(R.id.tradetargetbtn);
        tradetartgetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.diring);
//다이얼로그생성
//                final Dialog tradetargetnation = new Dialog( nation.this );
                tradetargetnation = new Dialog(nation.this);
                tradetargetnation.setContentView(R.layout.tradetargetnation);
tradetargetnation.setCancelable(false);
//setcontentview 를 먼저 연결해준 뒤에 텍스트뷰를 선언해야 널 에러가 안뜬다
                TextView message = (TextView) tradetargetnation.findViewById(R.id.gamenumber);
                message.setText("무역하고 싶은 나라는?");

                Button nation1 = (Button) tradetargetnation.findViewById(R.id.nation1);
                Button nation2 = (Button) tradetargetnation.findViewById(R.id.nation2);
                Button nation3 = (Button) tradetargetnation.findViewById(R.id.nation3);
                Button nation4 = (Button) tradetargetnation.findViewById(R.id.nation4);
                Button nation5 = (Button) tradetargetnation.findViewById(R.id.nation5);
                Button nation6 = (Button) tradetargetnation.findViewById(R.id.nation6);
//내가 선택한 나라 제외하고 버튼 띄우기
                switch (nationname) {
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
                                tradeconfirm(nationname, "대한민국");
                                MySoundPlayer.play(MySoundPlayer.diring);

                                //       Toast.makeText(getApplication(), "첫번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nation2:
                                tradeconfirm(nationname, "중국");
                                MySoundPlayer.play(MySoundPlayer.diring);

                                //                       Toast.makeText(getApplication(), "두번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nation3:
                                tradeconfirm(nationname, "호주");
                                MySoundPlayer.play(MySoundPlayer.diring);
                                //                       Toast.makeText(getApplication(), "세번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nation4:
                                tradeconfirm(nationname, "캐나다");
                                MySoundPlayer.play(MySoundPlayer.diring);
                                //                       Toast.makeText(getApplication(), "네번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nation5:
                                tradeconfirm(nationname, "사우디아라비아");
                                MySoundPlayer.play(MySoundPlayer.diring);
//                        Toast.makeText(getApplication(), "다섯번째 버튼입니다.", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nation6:
                                tradeconfirm(nationname, "남아프리카공화국");
                                MySoundPlayer.play(MySoundPlayer.diring);
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
                        MySoundPlayer.play(MySoundPlayer.diring);
                        tradetargetnation.dismiss();
                    }
                });
                tradetargetnation.show();
                MySoundPlayer.play(MySoundPlayer.confirm);
                //              tradetargetnation.setOnDismissListener((DialogInterface.OnDismissListener) nation.this);
            }
        });
// 실시간 데이터 감지
        final DocumentReference docRef = db.collection(gameId).document(nationname);
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
                    getsource(nationname);
                    goalsource(nationname);
                    Log.d(TAG, "Current data: " + snapshot.getData());
                    //  Toast.makeText(getApplication(), "Current data: " + snapshot.getData(), Toast.LENGTH_SHORT).show();
                    //상대국가 거래 요청 감지
                    requeststate = snapshot.getData().get("request").toString();
                    myallow = snapshot.getData().get("myallow").toString();
                    Log.e("myallow 업데이트", myallow.toString());
                    if (!requeststate.equals("0") && myallow.equals("0")) {
                        Log.e("myallow 업데이트2", myallow.toString());
                        tradeconfirm(nationname, requeststate.toString());
                    } else if (requeststate.equals("0") && myallow.equals("1")) {
                        loading();

                    } else if (!requeststate.equals("0") && myallow.equals("1")) {
                        loadingEnd();
                        Intent intent = new Intent(nation.this, tradewindow.class)
                                .setAction(Intent.ACTION_MAIN)
                                .addCategory(Intent.CATEGORY_LAUNCHER)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("requestnation", nationname);
                        intent.putExtra("targetnation", requeststate.toString());
                        intent.putExtra("gameId", gameId);
                        startActivity(intent);
                    } else if (requeststate.equals("0") && myallow.equals("0")) {
                        loadingEnd();
                        try {

                            tradeok.dismiss();
                        } catch (Exception exception) {
                        }

                    }
                } else {
                    Log.d(TAG, "Current data: null");
                }

// 로딩불러오기


            }
        });
    }  // 메인 끝
    @Override
    public void onBackPressed() {
        /* 다음 4가지 형태 중 하나 선택해서 사용 */

        backkeyHandler.onBackPressed();
        //backkeyHandler.onBackPressed("\'뒤로\' 버튼을 두 번 누르면 종료됩니다.\n입력한 내용이 지워집니다.");
        //backkeyHandler.onBackPressed(5);
//        backkeyHandler.onBackPressed("5초 내로 한번 더 누르세요", 5);
    }
//다이얼로그
//private void MakeDialog(String lv){
//    //다이얼로그생성
//    final Dialog lvup = new Dialog(nation.this);
//    lvup.setContentView(R.layout.confirmdialog);
//
//    TextView meg = (TextView) lvup.findViewById(R.id.confirmtitle);
//    meg.setText(" 레벨 "+ lv + " 로 업그레이드 가능합니다. 레벨 업 하시겠습니까?  ");
//
//    Button okbtn = (Button) lvup.findViewById(R.id.ok);
//    Button canclebtn = (Button) lvup.findViewById(R.id.cancel);
//
//
////확인버튼
//    okbtn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//             lvup.dismiss();
//
//            }
//
//    });
////취소버튼
//    canclebtn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//
//            MySoundPlayer.play(MySoundPlayer.diring);
//            lvup.dismiss();
//        }
//    });
//
//    lvup.show();
//    MySoundPlayer.play(MySoundPlayer.confirm);
//}



    private void getsource(final String name) {
        //파이어스토어에서 자료 가져오기
        db = FirebaseFirestore.getInstance();
        db.collection(gameId).document(name)
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
                            Object nationtech = document.getData().get("tech").toString();
                            Object nationName = document.getData().get("nationName").toString();

                            if(nationlv.equals("8")) {
//다이얼로그생성
                                final Dialog endgame = new Dialog(nation.this);
                                endgame.setContentView(R.layout.confirmdialog);
                                endgame.setCancelable(false);

                                TextView meg = (TextView) endgame.findViewById(R.id.confirmtitle);

                                meg.setText(" 무역왕 국가가 탄생하였습니다. 게임이 종료됩니다.  ");

                                Button okbtn = (Button) endgame.findViewById(R.id.ok);
                                Button canclebtn = (Button) endgame.findViewById(R.id.cancel);
                                canclebtn.setVisibility(View.GONE);

//확인버튼
                                okbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        MySoundPlayer.play(MySoundPlayer.diring);
                                        endgame.dismiss();

                                    }

                                });


                                endgame.show();
                                MySoundPlayer.play(MySoundPlayer.result);
                            }

                            nowlv.setText(nationlv.toString());
                            nowoil.setText(nationoil.toString());
                            nowfe.setText(nationfe.toString());
                            nowgold.setText(nationgold.toString());
                            nowwood.setText(nationwood.toString());
                            nowman.setText(nationman.toString());
                            nowmoney.setText(nationmoney.toString());

                            int lvnum = Integer.parseInt(nationlv.toString());
                            int oilnum = Integer.parseInt(nationoil.toString());
                            int fenum = Integer.parseInt(nationfe.toString());
                            int goldnum = Integer.parseInt(nationgold.toString());
                            int woodnum = Integer.parseInt(nationwood.toString());
                            int mannum = Integer.parseInt(nationman.toString());
                            int moneynum = Integer.parseInt(nationmoney.toString());
                            technum = Integer.parseInt(nationtech.toString());

                            //기술수준 이미지
                            switch (technum) {
                                case 0:
                                    imagetech.setImageResource(R.drawable.step0);
                                    break;
                                case 1:
                                    imagetech.setImageResource(R.drawable.step1);
                                    break;
                                case 2:
                                    imagetech.setImageResource(R.drawable.step2);
                                    break;
                                case 3:
                                    imagetech.setImageResource(R.drawable.step3);
                                    break;
                                case 4:
                                    imagetech.setImageResource(R.drawable.step4);
                                    break;
                                case 5:
                                    imagetech.setImageResource(R.drawable.step5);
                                    break;
                                case 6:
                                    imagetech.setImageResource(R.drawable.step6);
                                    break;
                                case 7:
                                    imagetech.setImageResource(R.drawable.step7);
                                    break;
                                case 8:
                                    imagetech.setImageResource(R.drawable.step8);
                                    break;
                            }

                            if (lvnum == 0 && technum >= 1 && woodnum >= 6 && mannum >= 6) {

                                //다이얼로그생성
                                final Dialog lvup = new Dialog(nation.this);
                                lvup.setContentView(R.layout.confirmdialog);
                                lvup.setCancelable(false);

                                TextView meg = (TextView) lvup.findViewById(R.id.confirmtitle);
                                meg.setText(" 레벨 1 로 업그레이드 가능합니다. 레벨 업 하시겠습니까?  ");

                                Button okbtn = (Button) lvup.findViewById(R.id.ok);
                                Button canclebtn = (Button) lvup.findViewById(R.id.cancel);
                                canclebtn.setVisibility(View.GONE);

//확인버튼
                                okbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //레벨을 1로 올리고 원래 자원에서 숫자 빼서 db에 업데이트
                                        MySoundPlayer.play(MySoundPlayer.diring);
                                        String woodnumber = String.valueOf(woodnum - 6);
                                        String mannumber = String.valueOf(mannum - 6);
                                        dbupdate3(name, "lv", "1", "wood", woodnumber, "man", mannumber);
                                        lvup.dismiss();

                                    }

                                });
//취소버튼
                                canclebtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        MySoundPlayer.play(MySoundPlayer.diring);
                                        lvup.dismiss();
                                    }
                                });

                                lvup.show();
                                MySoundPlayer.play(MySoundPlayer.confirm);

                            } else if (lvnum == 1 && technum >= 2 && oilnum >= 4 && mannum >= 8) {

                                //다이얼로그생성
                                final Dialog lvup = new Dialog(nation.this);
                                lvup.setContentView(R.layout.confirmdialog);
                                lvup.setCancelable(false);

                                TextView meg = (TextView) lvup.findViewById(R.id.confirmtitle);
                                meg.setText(" 레벨 2 로 업그레이드 가능합니다. 레벨 업 하시겠습니까?  ");

                                Button okbtn = (Button) lvup.findViewById(R.id.ok);
                                Button canclebtn = (Button) lvup.findViewById(R.id.cancel);
                                canclebtn.setVisibility(View.GONE);

//확인버튼
                                okbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        MySoundPlayer.play(MySoundPlayer.diring);
                                        //레벨을 2로 올리고 원래 자원에서 숫자 빼서 db 업데이트
                                        String oilnumber = String.valueOf(oilnum - 4);
                                        String mannumber = String.valueOf(mannum - 8);
                                        dbupdate3(name, "lv", "2", "oil", oilnumber, "man", mannumber);
                                        lvup.dismiss();

                                    }

                                });
//취소버튼
                                canclebtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        MySoundPlayer.play(MySoundPlayer.diring);
                                        lvup.dismiss();
                                    }
                                });

                                lvup.show();
                                MySoundPlayer.play(MySoundPlayer.confirm);


                            } else if (lvnum == 2 && technum >= 3 && oilnum >= 7 && goldnum >= 1 && woodnum >= 1 && mannum >= 3) {

                                //다이얼로그생성
                                final Dialog lvup = new Dialog(nation.this);
                                lvup.setContentView(R.layout.confirmdialog);
                                lvup.setCancelable(false);
                                TextView meg = (TextView) lvup.findViewById(R.id.confirmtitle);
                                meg.setText(" 레벨 3 으로 업그레이드 가능합니다. 레벨 업 하시겠습니까?  ");

                                Button okbtn = (Button) lvup.findViewById(R.id.ok);
                                Button canclebtn = (Button) lvup.findViewById(R.id.cancel);
                                canclebtn.setVisibility(View.GONE);

//확인버튼
                                okbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        MySoundPlayer.play(MySoundPlayer.diring);
                                        //
                                        String oilnumber = String.valueOf(oilnum - 7);
                                        String goldnumber = String.valueOf(goldnum - 1);
                                        String woodnumber = String.valueOf(woodnum - 1);
                                        String mannumber = String.valueOf(mannum - 3);
                                        dbupdate3(name, "lv", "3", "oil", oilnumber, "gold", goldnumber);
                                        dbupdate2(name, "wood", woodnumber, "man", mannumber);
                                        lvup.dismiss();

                                    }

                                });
//취소버튼
                                canclebtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        MySoundPlayer.play(MySoundPlayer.diring);
                                        lvup.dismiss();
                                    }
                                });

                                lvup.show();
                                MySoundPlayer.play(MySoundPlayer.confirm);


                            } else if (lvnum == 3 && technum >= 4 && fenum >= 8 && goldnum >= 1 && mannum >= 3) {

                                //다이얼로그생성
                                final Dialog lvup = new Dialog(nation.this);
                                lvup.setContentView(R.layout.confirmdialog);
                                lvup.setCancelable(false);
                                TextView meg = (TextView) lvup.findViewById(R.id.confirmtitle);
                                meg.setText(" 레벨 4 로 업그레이드 가능합니다. 레벨 업 하시겠습니까?  ");

                                Button okbtn = (Button) lvup.findViewById(R.id.ok);
                                Button canclebtn = (Button) lvup.findViewById(R.id.cancel);
                                canclebtn.setVisibility(View.GONE);

//확인버튼
                                okbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        MySoundPlayer.play(MySoundPlayer.diring);
                                        //
                                        String fenumber = String.valueOf(fenum - 8);
                                        String goldnumber = String.valueOf(goldnum - 1);
                                        String mannumber = String.valueOf(mannum - 3);
                                        dbupdate2(name, "lv", "4", "fe", fenumber);
                                        dbupdate2(name, "gold", goldnumber, "man", mannumber);
                                        lvup.dismiss();

                                    }

                                });
//취소버튼
                                canclebtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        MySoundPlayer.play(MySoundPlayer.diring);
                                        lvup.dismiss();
                                    }
                                });

                                lvup.show();
                                MySoundPlayer.play(MySoundPlayer.confirm);


                            } else if (lvnum == 4 && technum >= 5 && oilnum >= 2 && goldnum >= 2 && fenum >= 5 && mannum >= 3) {

                                //다이얼로그생성
                                final Dialog lvup = new Dialog(nation.this);
                                lvup.setContentView(R.layout.confirmdialog);
                                lvup.setCancelable(false);
                                TextView meg = (TextView) lvup.findViewById(R.id.confirmtitle);
                                meg.setText(" 레벨 5 로 업그레이드 가능합니다. 레벨 업 하시겠습니까?  ");

                                Button okbtn = (Button) lvup.findViewById(R.id.ok);
                                Button canclebtn = (Button) lvup.findViewById(R.id.cancel);
                                canclebtn.setVisibility(View.GONE);

//확인버튼
                                okbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        MySoundPlayer.play(MySoundPlayer.diring);
                                        //
                                        String oilnumber = String.valueOf(oilnum - 2);
                                        String goldnumber = String.valueOf(goldnum - 2);
                                        String fenumber = String.valueOf(fenum - 5);
                                        String mannumber = String.valueOf(mannum - 3);
                                        dbupdate3(name, "lv", "5", "oil", oilnumber, "gold", goldnumber);
                                        dbupdate2(name, "fe", fenumber, "man", mannumber);
                                        lvup.dismiss();

                                    }

                                });
//취소버튼
                                canclebtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        MySoundPlayer.play(MySoundPlayer.diring);
                                        lvup.dismiss();
                                    }
                                });

                                lvup.show();
                                MySoundPlayer.play(MySoundPlayer.confirm);


                            } else if (lvnum == 5 && technum >= 6 && oilnum >= 1 && goldnum >= 5 && fenum >= 4 && mannum >= 2) {

                                //다이얼로그생성
                                final Dialog lvup = new Dialog(nation.this);
                                lvup.setContentView(R.layout.confirmdialog);
                                lvup.setCancelable(false);
                                TextView meg = (TextView) lvup.findViewById(R.id.confirmtitle);
                                meg.setText(" 레벨 6 으로 업그레이드 가능합니다. 레벨 업 하시겠습니까?  ");

                                Button okbtn = (Button) lvup.findViewById(R.id.ok);
                                Button canclebtn = (Button) lvup.findViewById(R.id.cancel);
                                canclebtn.setVisibility(View.GONE);

//확인버튼
                                okbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        MySoundPlayer.play(MySoundPlayer.diring);
                                        //
                                        String oilnumber = String.valueOf(oilnum - 1);
                                        String goldnumber = String.valueOf(goldnum - 5);
                                        String fenumber = String.valueOf(fenum - 4);
                                        String mannumber = String.valueOf(mannum - 2);
                                        dbupdate3(name, "lv", "6", "oil", oilnumber, "gold", goldnumber);
                                        dbupdate2(name, "fe", fenumber, "man", mannumber);
                                        lvup.dismiss();

                                    }

                                });
//취소버튼
                                canclebtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        MySoundPlayer.play(MySoundPlayer.diring);
                                        lvup.dismiss();
                                    }
                                });

                                lvup.show();
                                MySoundPlayer.play(MySoundPlayer.confirm);


                            } else if (lvnum == 6 && technum >= 7 && oilnum >= 1 && goldnum >= 6 && fenum >= 3 && mannum >= 2) {

                                //다이얼로그생성
                                final Dialog lvup = new Dialog(nation.this);
                                lvup.setContentView(R.layout.confirmdialog);
                                lvup.setCancelable(false);
                                TextView meg = (TextView) lvup.findViewById(R.id.confirmtitle);
                                meg.setText(" 레벨 7 로 업그레이드 가능합니다. 레벨 업 하시겠습니까?  ");

                                Button okbtn = (Button) lvup.findViewById(R.id.ok);
                                Button canclebtn = (Button) lvup.findViewById(R.id.cancel);
                                canclebtn.setVisibility(View.GONE);

//확인버튼
                                okbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        MySoundPlayer.play(MySoundPlayer.diring);
                                        //
                                        String oilnumber = String.valueOf(oilnum - 1);
                                        String goldnumber = String.valueOf(goldnum - 6);
                                        String fenumber = String.valueOf(fenum - 3);
                                        String mannumber = String.valueOf(mannum - 2);
                                        dbupdate3(name, "lv", "7", "oil", oilnumber, "gold", goldnumber);
                                        dbupdate2(name, "fe", fenumber, "man", mannumber);
                                        lvup.dismiss();

                                    }

                                });
//취소버튼
                                canclebtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        MySoundPlayer.play(MySoundPlayer.diring);
                                        lvup.dismiss();
                                    }
                                });

                                lvup.show();
                                MySoundPlayer.play(MySoundPlayer.confirm);


                            } else if (lvnum == 7 && technum >= 8 && oilnum >= 3 && goldnum >= 2 && fenum >= 5 && woodnum >= 2 && mannum >= 3) {

                                //다이얼로그생성
                                final Dialog lvup = new Dialog(nation.this);
                                lvup.setContentView(R.layout.confirmdialog);
                                lvup.setCancelable(false);
                                TextView meg = (TextView) lvup.findViewById(R.id.confirmtitle);
                                meg.setText(" 레벨 8 로 업그레이드 가능합니다. 레벨 업 하시겠습니까?  ");

                                Button okbtn = (Button) lvup.findViewById(R.id.ok);
                                Button canclebtn = (Button) lvup.findViewById(R.id.cancel);

                                canclebtn.setVisibility(View.GONE);


//확인버튼
                                okbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        MySoundPlayer.play(MySoundPlayer.diring);
                                        //
                                        String oilnumber = String.valueOf(oilnum - 3);
                                        String goldnumber = String.valueOf(goldnum - 2);
                                        String fenumber = String.valueOf(fenum - 5);
                                        String woodnumber = String.valueOf(woodnum - 2);
                                        String mannumber = String.valueOf(mannum - 3);
                                        dbupdate3(name, "lv", "8", "oil", oilnumber, "gold", goldnumber);
                                        dbupdate3(name, "fe", fenumber, "man", mannumber, "wood", woodnumber);
                                        lvup.dismiss();

                                    }

                                });
//취소버튼
                                canclebtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        MySoundPlayer.play(MySoundPlayer.diring);
                                        lvup.dismiss();
                                    }
                                });

                                lvup.show();
                                MySoundPlayer.play(MySoundPlayer.confirm);


                            }


                        } else {
                            Log.d(TAG, "가져오기 실패", task.getException());
                            //   Toast.makeText(getApplication(), "이미 선택된 나라입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //db 1개 데이터 업데이트
    private void dbupdate(String name, String field1, String data1) {
        db.collection(gameId).document(name)
                .update(field1, data1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Log.d(TAG, "필드1 업데이트 성공함");
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
        db.collection(gameId).document(name)
                .update(field1, data1, field2, data2)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Log.d(TAG, "필드2 업데이트 성공함");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "쓰기 실패", e);
                    }
                });
    }

    //db 3개 데이터 업데이트
    private void dbupdate3(String name, String field1, String data1, String field2, String data2, String field3, String data3) {
        db.collection(gameId).document(name)
                .update(field1, data1, field2, data2, field3, data3)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Log.d(TAG, "필드3 업데이트 성공함");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "쓰기 실패", e);
                    }
                });
    }

    private void goalsource(String nationname) {
        //레벨에 따라 목적자원량 셋팅하기
        db = FirebaseFirestore.getInstance();
        db.collection(gameId).document(nationname)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Object nationlv = document.getData().get("lv").toString();
                            String nlv = (String) nationlv;
                            switch (nlv) {
                                case "0":
                                    goil.setText("0");
                                    gfe.setText("0");
                                    ggold.setText("0");
                                    gwood.setText("6");
                                    gman.setText("6");
                                    break;

                                case "1":
                                    goil.setText("4");
                                    gfe.setText("0");
                                    ggold.setText("0");
                                    gwood.setText("0");
                                    gman.setText("8");
                                    break;
                                case "2":
                                    goil.setText("7");
                                    gfe.setText("0");
                                    ggold.setText("1");
                                    gwood.setText("1");
                                    gman.setText("3");
                                    break;
                                case "3":
                                    goil.setText("0");
                                    gfe.setText("8");
                                    ggold.setText("1");
                                    gwood.setText("0");
                                    gman.setText("3");
                                    break;
                                case "4":
                                    goil.setText("2");
                                    gfe.setText("5");
                                    ggold.setText("2");
                                    gwood.setText("0");
                                    gman.setText("3");
                                    break;
                                case "5":
                                    goil.setText("1");
                                    gfe.setText("4");
                                    ggold.setText("5");
                                    gwood.setText("0");
                                    gman.setText("2");
                                    break;
                                case "6":
                                    goil.setText("1");
                                    gfe.setText("3");
                                    ggold.setText("6");
                                    gwood.setText("0");
                                    gman.setText("2");
                                    break;
                                case "7":
                                    goil.setText("3");
                                    gfe.setText("5");
                                    ggold.setText("2");
                                    gwood.setText("2");
                                    gman.setText("3");
                                    break;
                            }


                        } else {
                            Log.d(TAG, "가져오기 실패", task.getException());
                            //   Toast.makeText(getApplication(), "이미 선택된 나라입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //다이얼로그 나라 선택 확인
    public void tradeconfirm(final String nationname, final String targetnation) {
        //다이얼로그생성
        tradeok = new Dialog(this);
        tradeok.setContentView(R.layout.confirmdialog);
tradeok.setCancelable(false);
        TextView meg = (TextView) tradeok.findViewById(R.id.confirmtitle);
        if (myallow.equals("0") && !requeststate.equals("0")) {
            meg.setText(requeststate.toString() + "   에서 무역요청이 들어왔습니다. 수락할까요?");
        } else if (myallow.equals("0") && requeststate.equals("0")) {
            meg.setText(targetnation + "   과 무역할까요?");
        }

        Button okbtn = (Button) tradeok.findViewById(R.id.ok);
        Button canclebtn = (Button) tradeok.findViewById(R.id.cancel);
//다이얼로그 ok버튼 클릭액션
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MySoundPlayer.play(MySoundPlayer.diring);
//tradeok 다이얼로그가 사라질때 리스너를 달아서 액티비티에 신호 전달 ondismiss 매서드 실행
                tradeok.setOnDismissListener(nation.this);
                tradeok.dismiss();
                if (myallow.equals("0")) {
                    dbupdate(nationname, "myallow", "1");
                }
                Log.e("myallow 업데이트3", myallow.toString());
                traderequest(nationname, targetnation);
                Log.e("myallow 업데이트4", myallow.toString());

//ondismissListener 사용을 해보자                tradetargetnation.dismiss();
            }
        });

//다이얼로그 취소버튼 클릭액션
        canclebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySoundPlayer.play(MySoundPlayer.diring);
                dbupdate(targetnation, "myallow", "0");
                dbupdate2(nationname, "request", "0", "myallow", "0");

                tradeok.dismiss();
            }
        });

        tradeok.show();
        MySoundPlayer.play(MySoundPlayer.confirm);
    }

    //프로그레스 다이얼로그
    public void loading() {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = new ProgressDialog(nation.this);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("상대국가의 요청 수락을 기다리는 중입니다...");
                        progressDialog.setCancelable(false);

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        } else {
                            if(! nation.this.isFinishing()) {
                                progressDialog.show();
                            }
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

    //다이얼로그 나라선택 확인버튼 클릭시 요청국가 파이어스토어 업데이트
    public void traderequest(final String requestnation, final String targetnation) {
        db.collection(gameId).document(targetnation)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Object requeststate = document.getData().get("request").toString();

                            if (requeststate.equals("0")) {
//                                Log.d(TAG, "기록이 성공함"+requeststate);


                                db.collection(gameId).document(targetnation)
                                        .update("request", requestnation)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                // 실시간 데이터 감지
//                                                final DocumentReference docRef = db.collection("나라선택여부").document(requestnation);
//                                                docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                                                    @Override
//                                                    public void onEvent(@Nullable DocumentSnapshot snapshot,
//                                                                        @Nullable FirebaseFirestoreException e) {
//                                                        if (e != null) {
//                                                            Log.w(TAG, "Listen failed.", e);
//                                                            return;
//                                                        }
//
//                                                        if (snapshot != null && snapshot.exists()) {
//
//// 실시간 데이터변화 감지시 실행
//
//                                                            Object myrequeststate = snapshot.getData().get("request").toString();
//                                                            Object myallow2 = snapshot.getData().get("myallow").toString();
//                                                            Object my0 = snapshot.getData().get("0").toString();
//
//                                                            if (myrequeststate.equals("0") && myallow2.equals("1")) {
////                                                                loadingEnd();
//                                                                Log.e("데이터확인 로딩 전", "request"+myrequeststate.toString() +"  myallow"+myallow2);
//                                                                loading();
//                                                                Log.e("데이터확인 로딩 후", "request"+myrequeststate.toString() +"  myallow"+myallow2);
//                                                            }else if (myrequeststate.equals(targetnation) && myallow2.equals("1")) {
//                                                                Log.e("로딩 엔드 전  내 리퀘스트에 요청 들어옴", "request"+myrequeststate.toString() +"  myallow"+myallow2);
//                                                                loadingEnd();
//                                                                Log.e("로딩 엔드 후 내 리퀘스트에 요청 들어옴", "request"+myrequeststate.toString() +"  myallow"+myallow2);
////                                                                dbupdate(requestnation, "0", "1" );
////무역창 띄우기 인서트
//                                                                Intent intent = new Intent(nation.this, tradewindow.class);
//                                                                intent.putExtra("requestnation", requestnation);
//                                                                intent.putExtra("targetnation", targetnation);
//                                                                startActivity(intent);
//
//
//                                                                Log.d(TAG, "필드 업데이트 성공함");
//                                                            } else if (myrequeststate.equals("0") && myallow2.equals("0")) {
//                                                                Log.e("둘다 0", "request"+myrequeststate.toString() +"  myallow"+myallow2);
//                                                                loadingEnd();
//                                                                Log.e("둘다0 로딩앤드 후", "request"+myrequeststate.toString() +"  myallow"+myallow2);
//                                                            }
//                                                        } else {
//                                                            Log.d(TAG, "Current data: null");
//                                                        }
//                                                    }
//                                                });


                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "쓰기 실패", e);
                                            }
                                        });

                            } else {
                                Log.d(TAG, "현재 다른 나라와 무역중임 - " + targetnation + " 과 무역중인 나라:  " + requeststate);
                                if (requeststate.equals(requestnation) && myallow.equals("1")) {
                                    //이미 나와 거래중인데 무역창이 실수로 꺼질경우 대비

//무역창 띄우기 인서트
                                    Intent intent = new Intent(nation.this, tradewindow.class)
                                            .setAction(Intent.ACTION_MAIN)
                                            .addCategory(Intent.CATEGORY_LAUNCHER)
                                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("requestnation", requestnation);
                                    intent.putExtra("targetnation", targetnation);
                                    intent.putExtra("gameId", gameId);
                                    startActivity(intent);

                                    Toast.makeText(getApplication(), "현재 우리나라와 무역중이었음. 무역창으로 다시 이동합니다.", Toast.LENGTH_SHORT).show();
                                } else {
                                    dbupdate2(requestnation, "request", "0", "myallow", "0");
                                    loadingEnd();
                                    Log.d(TAG, "현재 다른 나라와 무역중임 - " + targetnation + " 과 무역중인 나라:  " + requeststate);
                                    Toast.makeText(getApplication(), "현재 다른 나라와 무역중임 - " + targetnation + " 과 무역중인 나라:  " + requeststate, Toast.LENGTH_SHORT).show();
                                }


                            }

                        } else {
                            Log.d(TAG, "가져오기 실패", task.getException());
                            Toast.makeText(getApplication(), "자료 업데이트 실패.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        try {
            tradetargetnation.dismiss();
        } catch (Exception e) {
        }
    }
}
