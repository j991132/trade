package com.social.trade;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankViewHolder> {

    private ArrayList<Rank> arrayList;
    private Context context;
    //어댑터에서 액티비티 액션을 가져올 때 context가 필요한데 어댑터에는 context가 없다.
    //선택한 액티비티에 대한 context를 가져올 때 필요하다.

    public RankAdapter(ArrayList<Rank> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }


    @NonNull
    @Override
    //실제 리스트뷰가 어댑터에 연결된 다음에 뷰 홀더를 최초로 만들어낸다.
    public RankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rank_item, parent, false);
        RankViewHolder holder = new RankViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RankViewHolder holder, int position) {

        Rank rank = arrayList.get(position);

        holder.nationname.setText(rank.getNationName());
        holder.nationlv.setText(rank.getLv());
        holder.teamname.setText(rank.getTeamname());
//        holder.nationname.setText(arrayList.get(position).getNationName());
//        holder.nationlv.setText(arrayList.get(position).getLv());
//        holder.teamname.setText(arrayList.get(position).getTeamname());
        switch (rank.getNationName()){
            case "대한민국" :
holder.nationimage.setImageResource(R.drawable.kor);
                break;
            case "남아프리카공화국" :
                holder.nationimage.setImageResource(R.drawable.sa);
                break;
            case "중국" :
                holder.nationimage.setImageResource(R.drawable.cha);
               break;
            case "사우디아라비아" :
                holder.nationimage.setImageResource(R.drawable.saudi);
                break;
            case "캐나다" :
                holder.nationimage.setImageResource(R.drawable.ca);
                break;
            case "호주" :
                holder.nationimage.setImageResource(R.drawable.os);
                break;
        }
    }

    @Override
    public int getItemCount() {
        // 삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class RankViewHolder extends RecyclerView.ViewHolder {
        ImageView nationimage;
        TextView nationname, nationlv, teamname;

        public RankViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nationimage = itemView.findViewById(R.id.nationimage);
            this.nationname = itemView.findViewById(R.id.nationname);
            this.nationlv = itemView.findViewById(R.id.nationlv);
            this.teamname = itemView.findViewById(R.id.teamname);

        }
    }
}
