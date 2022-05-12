package com.social.trade;

import static java.security.AccessController.getContext;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_list);

//        progressDialog = new ProgressDialog(this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Fetching data");
//        progressDialog.show();


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<Rank>();
        db = FirebaseFirestore.getInstance();

        adapter = new RankAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);
//        EventChangeListener();

        db.collection("나라선택여부").orderBy("lv", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                        Log.e("RankList 액티비티", "Listen failed.", e);
                    return;

                }
//                arrayList.clear();
                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType()==DocumentChange.Type.ADDED) {
                        arrayList.add(dc.getDocument().toObject(Rank.class));
                        Log.e("RankList arrayList", ""+arrayList, e);
                    }
                    //어답터 갱신
                    adapter.notifyDataSetChanged();
                    if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                    }
                }
                }

            }
        });
        Log.e("RankList arrayList", ""+arrayList);
    }

}