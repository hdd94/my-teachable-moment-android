package com.example.android.firebaseauthdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShowUserRankingBestRatingActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private ImageButton buttonChange;

    private Toolbar toolbar;

    private RecyclerView recyclerView;
    //TODO: TeachableMoment anzeigen mit User anzeigen wechseln!
    private UserRankingBestRankingAdapter mAdapter;
    List<_TeachableMomentInformation> tmList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_ranking_best_rating);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, ShowTitleScreen.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("UnconfirmedMoments");

        buttonChange = (ImageButton) findViewById(R.id.imageButtonChange);
        buttonChange.setOnClickListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("User-Ranking");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        // adding inbuilt divider line
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                _TeachableMomentInformation tm = tmList.get(position);
//                Toast.makeText(getApplicationContext(), tm.getUserNickname() + " is selected!", Toast.LENGTH_SHORT).show();

                callTeachableMoment(tm);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onClick(View view) {
        if (view == buttonChange) {
            finish();
            startActivity(new Intent(this, ShowUserRankingMostMomentsActivity.class));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tmList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    _TeachableMomentInformation tm = postSnapshot.getValue(_TeachableMomentInformation.class);
                    if (tm.getAverageRating() > 0 && tm.isConfirmed()) tmList.add(tm);
                }

                sortDataHighestRatingAndName();
                filterUserHighestRating(tmList);
                sortDataHighestRating();

                mAdapter = new UserRankingBestRankingAdapter(tmList);
                recyclerView.setAdapter(mAdapter);

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sortDataHighestRatingAndName() {
        Collections.sort(tmList, new Comparator<_TeachableMomentInformation>() {
            public int compare(_TeachableMomentInformation t1, _TeachableMomentInformation t2) {
                int temp = t2.getUserNickname().compareTo(t1.getUserNickname());
                if(temp == 0) {
                    return Float.compare(t2.getAverageRating(), t1.getAverageRating());
//                    temp = String.valueOf(t2.getAverageRating()).compareTo(String.valueOf(t1.getAverageRating()));
                }
                return temp;
            }
        });
    }

    private void filterUserHighestRating(List<_TeachableMomentInformation> tmList) {
        List<_TeachableMomentInformation> tmListFiltered = tmList;
        List<_TeachableMomentInformation> tmListNew = new ArrayList<>();

        _TeachableMomentInformation tm = tmListFiltered.get(0);
        tmListNew.add(tm);

        for (int i = 0; i < tmListFiltered.size(); i++) {
            _TeachableMomentInformation temp = tmListFiltered.get(i);
            if(!(temp.getUserNickname().equals(tm.getUserNickname()))) {
                tm = temp;
                tmListNew.add(tm);
            }
        }

        this.tmList = tmListNew;
    }

    private void sortDataHighestRating() {
        Collections.sort(tmList, new Comparator<_TeachableMomentInformation>() {
            public int compare(_TeachableMomentInformation t1, _TeachableMomentInformation t2) {
                int temp = Float.compare(t2.getAverageRating(), t1.getAverageRating());
//                return String.valueOf(t2.getAverageRating()).compareTo(String.valueOf(t1.getAverageRating()));
                if (temp == 0) {
                    return t1.getUserNickname().compareTo(t2.getUserNickname());
                }
                return temp;
            }
        });
    }

    private void callTeachableMoment(_TeachableMomentInformation tm) {

        Intent intent = new Intent(this, ShowOneTeachableMomentActivity.class);
        intent.putExtra("TeachableMoment", tm);
        startActivity(intent);
    }

    //TODO: RecyclerViews sortieren

    //TODO Prüfen, ob es funktioniert, dass der Zurückbutton auf die vorherige Activity zeigt
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
