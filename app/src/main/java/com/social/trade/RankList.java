package com.social.trade;

import static java.security.AccessController.getContext;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RankList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Rank> arrayList;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;
    private View view;
    private String gameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_list);

        MySoundPlayer.initSounds(getApplicationContext());

        //인텐트 값 가져오기
        Intent intent = getIntent();
        gameId = intent.getStringExtra("gameId");

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching data");
        progressDialog.show();


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<Rank>();
        db = FirebaseFirestore.getInstance();

        adapter = new RankAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);
        EventChangeListener();


    }

    private void EventChangeListener() {
        db.collection(gameId).orderBy("lv", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {

                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                                Log.e("RankList 액티비티", "Listen failed.", e);
                                return;

                            }
                        }
//                arrayList.clear();
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                arrayList.add(dc.getDocument().toObject(Rank.class));
                                Log.e("RankList arrayList", "" + arrayList, e);
                            } else if (dc.getType() == DocumentChange.Type.MODIFIED) {
                                Log.e("문서내용 업데이트", dc.getOldIndex()+"    "+dc.getNewIndex());

                                if (dc.getOldIndex() != dc.getNewIndex()) {
                                    MySoundPlayer.play(MySoundPlayer.opening);
                                }
                                arrayList.remove(dc.getOldIndex());
                                arrayList.add(dc.getNewIndex(), dc.getDocument().toObject(Rank.class));
                                adapter.notifyItemMoved(dc.getOldIndex(), dc.getNewIndex());
                                if(dc.getDocument().getData().get("lv").equals("8")){
                                    //다이얼로그생성
                                    final Dialog endgameDialog = new Dialog(RankList.this);
                                    endgameDialog.setContentView(R.layout.confirmdialog);
                                    endgameDialog.setCancelable(false);

                                    TextView meg = (TextView) endgameDialog.findViewById(R.id.confirmtitle);
                                    String winner = (String) dc.getDocument().getData().get("nationName");
                                    meg.setText(winner+" (이)가 8레벨에 먼저 도달하였습니다. 우승을 축하합니다.  ");

                                    Button okbtn = (Button) endgameDialog.findViewById(R.id.ok);
                                    Button canclebtn = (Button) endgameDialog.findViewById(R.id.cancel);
                                    canclebtn.setVisibility(View.GONE);

//확인버튼
                                    okbtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            MySoundPlayer.play(MySoundPlayer.diring);
                                           endgameDialog.dismiss();

                                        }

                                    });


                                    endgameDialog.show();
                                    MySoundPlayer.play(MySoundPlayer.result);
//                                    Toast.makeText(getApplication(), "8레벨에 도달하였습니다.", Toast.LENGTH_SHORT).show();
                                }
//                                    arrayList.set(dc.getOldIndex(), dc.getDocument().toObject(Rank.class));
//                                    adapter.notifyItemChanged(dc.getOldIndex());
//                                }
//                                arrayList.add(dc.getDocument().toObject(Rank.class));
                                Log.e("RankList arrayList2", "" + arrayList, e);
                            }
                            //어답터 갱신
                            adapter.notifyDataSetChanged();
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                        }
                    }


                });
        Log.e("RankList arrayList3", "" + arrayList);
    }

}