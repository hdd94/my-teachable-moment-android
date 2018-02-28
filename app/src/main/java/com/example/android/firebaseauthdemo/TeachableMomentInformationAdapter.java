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

public class TeachableMomentInformationAdapter extends RecyclerView.Adapter <TeachableMomentInformationAdapter.MyViewHolder> {

    private List<TeachableMomentInformation> tmList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, rating, user;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            user = (TextView) view.findViewById(R.id.user);
//            rating = (TextView) view.findViewById(R.id.rating);
        }
    }


    public TeachableMomentInformationAdapter(List<TeachableMomentInformation> tmList) {
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
        TeachableMomentInformation tm = tmList.get(position);
        holder.title.setText(tm.getTitle());
        holder.user.setText("Nickname des Users");
//        holder.rating.setText("#00");
    }

    @Override
    public int getItemCount() {
        return tmList.size();
    }

}
