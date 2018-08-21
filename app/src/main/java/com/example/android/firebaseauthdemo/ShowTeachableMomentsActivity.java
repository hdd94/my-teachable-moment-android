package com.example.android.firebaseauthdemo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShowTeachableMomentsActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private ImageButton buttonCreate;
    private ImageButton buttonRanking;

    private AlertDialog alertDialogSort;
    private CharSequence[] sort_values = {"  Nickname", "  Aktuelle Beiträge","  Höchste Bewertung","  Meiste Bewertungen"};
    private CharSequence[] sort_values_admin = {"  Confirmed","  Nickname", "  Aktuelle Beiträge","  Höchste Bewertung","  Meiste Bewertungen"};

    private boolean ascendingConfirmed = true;
    private boolean ascendingNickname = true;
    private boolean ascendingCurrentPost = true;
    private boolean ascendingHighestRating = true;
    private boolean ascendingMostRatings = true;

    private boolean adminStatus = false;

    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private TeachableMomentInformationAdapter mAdapter;
    List<_TeachableMomentInformation> tmList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!isNetworkAvailable()) {
            Toast.makeText(getApplicationContext(), "Bitte stellen Sie eine stabile Internetverbindung zur Verfügung!!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setContentView(R.layout.activity_show_teachable_moments);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, ShowTitleScreen.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        buttonCreate = (ImageButton) findViewById(R.id.imageButtonCreate);
        buttonCreate.setVisibility(View.GONE);
        buttonCreate.setOnClickListener(this);
        buttonRanking = (ImageButton) findViewById(R.id.imageButtonUserRanking);
        buttonRanking.setVisibility(View.GONE);
        buttonRanking.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Teachable Moments");
        setSupportActionBar(toolbar);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                _TeachableMomentInformation tm = tmList.get(position);
                callTeachableMoment(tm);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        checkAdminStatusAndShowTM();
    }

    private void checkAdminStatusAndShowTM() {

        String userID = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child("Benutzer").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                _UserInformation u = dataSnapshot.getValue(_UserInformation.class);
                if (u != null) {
                    adminStatus = u.getAdminStatus();
                    checkUser(adminStatus);
                }
                else {
                    Intent i = new Intent(getApplicationContext(), ShowTitleScreen.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    firebaseAuth.signOut();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void checkUser(final boolean adminStatus) {
        databaseReference.child("UnconfirmedMoments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tmList.clear();

                if (adminStatus == true) {
                    buttonRanking.setVisibility(View.VISIBLE);
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        _TeachableMomentInformation tm = postSnapshot.getValue(_TeachableMomentInformation.class);
                        tmList.add(tm);}
                }
                else {
                    buttonRanking.setVisibility(View.VISIBLE);
                    buttonCreate.setVisibility(View.VISIBLE);
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        _TeachableMomentInformation tm = postSnapshot.getValue(_TeachableMomentInformation.class);
                        if (tm.isConfirmed()) tmList.add(tm);}
                }

                Collections.sort(tmList, new Comparator<_TeachableMomentInformation>() {
                        public int compare(_TeachableMomentInformation t1, _TeachableMomentInformation t2) {
                        Date[] dateArray = parseStringToDate(t2.getDate(), t1.getDate());
                        if (dateArray[0] == null || dateArray[1] == null)
                            return 0;
                        return dateArray[0].compareTo(dateArray[1]);
                    }
                });

                mAdapter = new TeachableMomentInformationAdapter(tmList, "CurrentPost+Nickname");
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void callTeachableMoment(_TeachableMomentInformation tm) {
        Intent intent = new Intent(this, ShowOneTeachableMomentActivity.class);
        intent.putExtra("TeachableMoment", tm);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonCreate) startActivity(new Intent(this, CreateTeachableMomentActivity.class));
        if (view == buttonRanking) startActivity(new Intent(this, ShowUserRankingBestRatingActivity.class));
    }

    // Appbar Icons initialisieren
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // OnClickListener von den Appbar Icons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                showAlertDialogSort();
                return true;

            case R.id.action_settings:
                startActivity(new Intent(this, ShowMenuActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showAlertDialogSort(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Sortieren nach...");
        if (adminStatus) {
            builder.setItems(sort_values_admin, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch(i)
                    {
                        case 0: //Nach Confirmed sortiert - Confirmed
                            sortDataConfirmed(ascendingConfirmed);
                            ascendingConfirmed = !ascendingConfirmed;
                            break;

                        case 1: //Nach Nickname sortiert - Nickname
                            sortDataNickname(ascendingNickname);
                            ascendingNickname = !ascendingNickname;
                            break;

                        case 2: //Aktuellste Beiträge - Current
                            sortDataCurrentPost(ascendingCurrentPost);
                            ascendingCurrentPost = !ascendingCurrentPost;
                            break;

                        case 3: //Höchstbewertete Beiträge - HighestRating
                            sortDataHighestRating(ascendingHighestRating);
                            ascendingHighestRating = !ascendingHighestRating;
                            break;

                        case 4: //Meistbewertete Beiträge - MostRatings
                            sortDataMostRatings(ascendingMostRatings);
                            ascendingMostRatings = !ascendingMostRatings;
                            break;
                    }
                    alertDialogSort.dismiss();
                }
            });
        } else {
            builder.setItems(sort_values, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch(i)
                    {
//                        case 0: //Nach eigene Beiträge sortiert - Confirmed
//                            sortDataOwnPost(ascendingOwnPost);
//                            ascendingOwnPost = !ascendingOwnPost;
//                            break;

                        case 0: //Nach Nickname sortiert - Nickname
                            sortDataNickname(ascendingNickname);
                            ascendingNickname = !ascendingNickname;
                            break;

                        case 1: //Aktuellste Beiträge - Current
                            sortDataCurrentPost(ascendingCurrentPost);
                            ascendingCurrentPost = !ascendingCurrentPost;
                            break;

                        case 2: //Höchstbewertete Beiträge - HighestRating
                            sortDataHighestRating(ascendingHighestRating);
                            ascendingHighestRating = !ascendingHighestRating;
                            break;

                        case 3: //Meistbewertete Beiträge - MostRatings
                            sortDataMostRatings(ascendingMostRatings);
                            ascendingMostRatings = !ascendingMostRatings;
                            break;
                    }
                    alertDialogSort.dismiss();
                }
            });
        }

        alertDialogSort = builder.create();
        alertDialogSort.show();

    }

    private void sortDataConfirmed(boolean ascendingConfirmed) {
        if(ascendingConfirmed) {
            Collections.sort(tmList, new Comparator<_TeachableMomentInformation>() {
                public int compare(_TeachableMomentInformation t1, _TeachableMomentInformation t2) {
                    int temp = Boolean.compare(t2.isConfirmed(), t1.isConfirmed());
                    if (temp == 0) {
                        return t1.getUserNickname().compareTo(t2.getUserNickname());
                    }
                    return temp;
                }
            });
        }
        else {
            Collections.reverse(tmList);
        }

        mAdapter = new TeachableMomentInformationAdapter(tmList, "Confirmed");
//        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);
    }

    private void sortDataNickname(boolean ascendingNickname) {
        if(ascendingNickname) {
            Collections.sort(tmList, new Comparator<_TeachableMomentInformation>() {
                public int compare(_TeachableMomentInformation t1, _TeachableMomentInformation t2) {
                    return t2.getUserNickname().compareTo(t1.getUserNickname());
                }
            });
        }
        else {
            Collections.reverse(tmList);
        }

        mAdapter = new TeachableMomentInformationAdapter(tmList, "Nickname+CurrentPost");
//        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);
    }

    private void sortDataCurrentPost(boolean ascendingCurrentPost) {
        if(ascendingCurrentPost) {
            Collections.sort(tmList, new Comparator<_TeachableMomentInformation>() {
                public int compare(_TeachableMomentInformation t1, _TeachableMomentInformation t2) {
                    Date[] dateArray = parseStringToDate(t2.getDate(), t1.getDate());
                    if (dateArray[0] == null || dateArray[1] == null)
                        return 0;
                    return dateArray[0].compareTo(dateArray[1]);
                }
            });
        }
        else {
            Collections.reverse(tmList);
        }

        mAdapter = new TeachableMomentInformationAdapter(tmList, "CurrentPost+Nickname");
        recyclerView.setAdapter(mAdapter);
    }

    private Date[] parseStringToDate(String d1, String d2) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        Date _d1 = null;
        Date _d2 = null;
        try {
            _d1 = format.parse(d1);
            _d2 = format.parse(d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date[] dateArray = {_d1, _d2};
        return dateArray;
    }

    private void sortDataHighestRating(boolean ascendingHighestRating) {
        if(ascendingHighestRating) {
            Collections.sort(tmList, new Comparator<_TeachableMomentInformation>() {
                public int compare(_TeachableMomentInformation t1, _TeachableMomentInformation t2) {
                    return String.valueOf(t2.getAverageRating()).compareTo(String.valueOf(t1.getAverageRating()));
                }
            });
        }
        else {
            Collections.reverse(tmList);
        }

        mAdapter = new TeachableMomentInformationAdapter(tmList, "HighestRating");
//        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);
    }

    private void sortDataMostRatings(boolean ascendingMostRatings) {

        if(ascendingMostRatings) {
            Collections.sort(tmList, new Comparator<_TeachableMomentInformation>() {
                public int compare(_TeachableMomentInformation t1, _TeachableMomentInformation t2) {
                    return String.valueOf(t2.getCountRatings()).compareTo(String.valueOf(t1.getCountRatings()));
                }
            });
        }
        else {
            Collections.reverse(tmList);
        }

        mAdapter = new TeachableMomentInformationAdapter(tmList, "MostRatings");
//        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
