package com.example.android.firebaseauthdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by duc on 15.02.18.
 */

public class UserRankingBestRankingAdapter extends RecyclerView.Adapter <UserRankingBestRankingAdapter.MyViewHolder> {

    private List<_TeachableMomentInformation> tmList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nickname, titel, rating;

        public MyViewHolder(View view) {
            super(view);
            nickname = (TextView) view.findViewById(R.id.nickname);
            titel = (TextView) view.findViewById(R.id.count);
            rating = (TextView) view.findViewById(R.id.rating);
        }
    }


    public UserRankingBestRankingAdapter(List<_TeachableMomentInformation> tmList) {
        this.tmList = tmList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_user_ranking_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        _TeachableMomentInformation tm = tmList.get(position);
        holder.nickname.setText(tm.getUserNickname());
        holder.titel.setText(tm.getTitle());
        holder.rating.setText("Ø" + String.valueOf(tm.getAverageRating()));
    }

    @Override
    public int getItemCount() {
        return tmList.size();
    }

}
