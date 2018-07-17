package com.example.android.firebaseauthdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ShowOneTeachableMomentActivity extends AppCompatActivity{

    private EditText editTextTitle;
    private EditText editTextRating;
    private EditText editTextTeachableMoment;
    private EditText editTextPlace;
    private EditText editTextDate;
    private RatingBar ratingBar;
    private Toolbar toolbar;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private _TeachableMomentInformation tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_one_teachable_moment);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), ShowTitleScreen.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();


        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextTitle.setEnabled(false);
        editTextRating = (EditText) findViewById(R.id.editTextRating);
        editTextRating.setEnabled(false);
        editTextTeachableMoment = (EditText) findViewById(R.id.editTextTeachableMoment);
        editTextTeachableMoment.setKeyListener(null);
        editTextPlace = (EditText) findViewById(R.id.editTextPlace);
        editTextPlace.setEnabled(false);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextDate.setEnabled(false);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Teachable Moment");
        setSupportActionBar(toolbar);
        //Zurück-Button oben links
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tm = (_TeachableMomentInformation) getIntent().getParcelableExtra("TeachableMoment");
        editTextTitle.setText(tm.getTitle());
        editTextRating.setText("Ø" + String.valueOf(tm.getAverageRating()));
        editTextRating.setTextColor(Color.GRAY);
        editTextTeachableMoment.setText(tm.getTeachableMoment());
        editTextPlace.setText(tm.getPlace());
        editTextDate.setText(tm.getDate());

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        final DatabaseReference dbRating = databaseReference.child("UnconfirmedMoments").child(tm.getId()).child("ratings");
        boolean userHasRated = false;

        dbRating.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userID = firebaseAuth.getCurrentUser().getUid();
                boolean existUserID;

                for(DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()){
                    existUserID = uniqueKeySnapshot.getValue().toString().contains(userID);

                    if (existUserID) {
                        _RatingInformation r = uniqueKeySnapshot.getValue(_RatingInformation.class);
                        ratingBar.setRating(((float) r.getRating()));
                        ratingBar.setIsIndicator(true);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                final int ratingINT = (int) rating;
                //Überlegen, ob User nachträglich Rating verändern sollte, da Admin dann ändern kann.
                //Implementieren, wenn ratingID vorhanden ist, dann braucht man keine neue zu erstellen (Ändern der Bewertung)
                String ratingID = dbRating.push().getKey();
                String userID = firebaseAuth.getCurrentUser().getUid();
                String creationDate = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss").format(Calendar.getInstance().getTime());
                _RatingInformation ratingInformation = new _RatingInformation(ratingINT, ratingID, userID, creationDate);

                dbRating.child(ratingInformation.getUserID()).setValue(ratingInformation).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        updateAverageRating(ratingINT, dbRating);
                    }
                });
            }
        });
    }

    // Summarize ratings, calculate average(rounded) and set DB
    private void updateAverageRating(int ratingINT, DatabaseReference dbRating) {
        dbRating.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double average = 0.0;
                double total = 0.0;
                double count = 0.0;

                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    double rating = Double.parseDouble(ds.child("rating").getValue().toString());
                    total = total + rating;
                    count = count + 1;
                    average = Math.floor((total / count) * 100) / 100;
                }
                databaseReference.child("UnconfirmedMoments").child(tm.getId()).child("averageRating").setValue(average);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}