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

public class UserRankingMostMomentsAdapter extends RecyclerView.Adapter <UserRankingMostMomentsAdapter.MyViewHolder> {

    private List<_UserInformation> uList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nickname, count, rating;

        public MyViewHolder(View view) {
            super(view);
            nickname = (TextView) view.findViewById(R.id.nickname);
            count = (TextView) view.findViewById(R.id.count);
            rating = (TextView) view.findViewById(R.id.rating);
        }
    }


    public UserRankingMostMomentsAdapter(List<_UserInformation> uList) {
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
        _UserInformation um = uList.get(position);
        holder.nickname.setText(um.getNickname());
        String label = null;
        if (um.tmCounter == 1) label = " Beitrag"; else label = " Beiträge";
        holder.count.setText(String.valueOf(um.getTmCounter()) + label);
        holder.rating.setText(String.valueOf("#" + (position + 1)));
    }

    @Override
    public int getItemCount() {
        return uList.size();
    }

}
