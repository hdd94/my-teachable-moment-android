package com.example.android.firebaseauthdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by duc on 15.02.18.
 */

public class TeachableMomentInformationAdapter extends RecyclerView.Adapter <TeachableMomentInformationAdapter.MyViewHolder> {

    private List<_TeachableMomentInformation> tmList;
    private String sortType;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, user, rating;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            user = (TextView) view.findViewById(R.id.user);
//            rating = (TextView) view.findViewById(R.id.rating);
        }
    }


    public TeachableMomentInformationAdapter(List<_TeachableMomentInformation> tmList, String sortType) {
        this.tmList = tmList;
        this.sortType = sortType;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_teachablemomentinformation_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        _TeachableMomentInformation tm = tmList.get(position);
        holder.title.setText(tm.getTitle());
        try {
            switch (sortType) {
                case "Confirmed":
                    if (tm.isConfirmed()) holder.user.setText("Bestätigt " + "(" + tm.getUserNickname() + ")");
                    else holder.user.setText("Nicht bestätigt " + "(" + tm.getUserNickname() + ")");
                    break;
                case "CurrentPost+Nickname":
                    holder.user.setText(tm.getDate() + " | " + tm.getUserNickname());
                    break;
                case "Nickname+CurrentPost":
                    holder.user.setText(tm.getUserNickname() + " | " + tm.getDate());
                    break;
                case "HighestRating":
                    holder.user.setText(String.valueOf(tm.getAverageRating()));
                    break;
                case "MostRatings":
                    holder.user.setText(String.valueOf(tm.getCountRatings()));
                    break;
            }
        } catch (NullPointerException npe) {
        }
    }

    @Override
    public int getItemCount() {
        return tmList.size();
    }

}
