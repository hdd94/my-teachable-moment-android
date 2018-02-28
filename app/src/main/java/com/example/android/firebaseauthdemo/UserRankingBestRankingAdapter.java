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

    private List<UserInformation> uList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nickname, titel, rating;

        public MyViewHolder(View view) {
            super(view);
            nickname = (TextView) view.findViewById(R.id.nickname);
            titel = (TextView) view.findViewById(R.id.count);
            rating = (TextView) view.findViewById(R.id.rating);
        }
    }


    public UserRankingBestRankingAdapter(List<UserInformation> uList) {
        this.uList = uList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_user_ranking_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        UserInformation um = uList.get(position);
        holder.nickname.setText(um.getNickname());
        holder.titel.setText("Titel des Beitrags");
        holder.rating.setText("Ø4,93");
    }

    @Override
    public int getItemCount() {
        return uList.size();
    }

}
