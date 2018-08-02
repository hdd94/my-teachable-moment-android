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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, user, rating;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            user = (TextView) view.findViewById(R.id.user);
//            rating = (TextView) view.findViewById(R.id.rating);
        }
    }


    public TeachableMomentInformationAdapter(List<_TeachableMomentInformation> tmList) {
        this.tmList = tmList;
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
//            holder.user.setText(tm.getNickname());
            holder.user.setText(tm.getDate());
        } catch (NullPointerException npe) {
            // It's fine if findUser throws a NPE
        }
    }

    @Override
    public int getItemCount() {
        return tmList.size();
    }

}
