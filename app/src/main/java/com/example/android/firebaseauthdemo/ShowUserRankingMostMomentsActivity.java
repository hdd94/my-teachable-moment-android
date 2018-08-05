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

public class ShowUserRankingMostMomentsActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private ImageButton buttonChange;

    private Toolbar toolbar;

    private RecyclerView recyclerView;
    //TODO: TeachableMoment anzeigen mit User anzeigen wechseln!
    private UserRankingMostMomentsAdapter mAdapter;
    List<_UserInformation> umList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_ranking_most_moments);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, ShowTitleScreen.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Benutzer");

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
//                _UserInformation um = umList.get(position);
//                Toast.makeText(getApplicationContext(), um.getNickname() + " is selected!", Toast.LENGTH_SHORT).show();
//                callTeachableMoment(tm);
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
            startActivity(new Intent(this, ShowUserRankingBestRatingActivity.class));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                umList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    _UserInformation um = postSnapshot.getValue(_UserInformation.class);
                    if (um.getTmCounter() != 0) umList.add(um);
                }

                sortMostMoments();

                mAdapter = new UserRankingMostMomentsAdapter(umList);
                recyclerView.setAdapter(mAdapter);

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sortMostMoments() {
        Collections.sort(umList, new Comparator<_UserInformation>() {
            public int compare(_UserInformation u1, _UserInformation u2) {
                int temp = String.valueOf(u2.getTmCounter()).compareTo(String.valueOf(u1.getTmCounter()));
                if(temp == 0) {
                    return u1.getNickname().compareTo(u2.getNickname());
                }
                return temp;
            }
        });
    }

//    private void callTeachableMoment(_TeachableMomentInformation tm) {
//
//        Intent intent = new Intent(this, ShowOneTeachableMomentActivity.class);
//        intent.putExtra("TeachableMoment", tm);
//        startActivity(intent);
//
//    }
    //TODO: RecyclerViews sortieren

    //TODO Prüfen, ob es funktioniert, dass der Zurückbutton auf die vorherige Activity zeigt
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
