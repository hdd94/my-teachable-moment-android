package com.example.android.firebaseauthdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ShowOneTeachableMomentActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextTitle;
    private EditText editTextRating;
    private EditText editTextTeachableMoment;
    private EditText editTextUserNickname;
    private EditText editTextDate;
    private Button buttonConfirm;
    private Button buttonDelete;
    private RatingBar ratingBar;
    private Toolbar toolbar;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private boolean adminStatus;
    private _TeachableMomentInformation tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), ShowTitleScreen.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        checkAdminStatusAndSetView();
    }

    private void checkAdminStatusAndSetView() {
        String userID = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child("Benutzer").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                _UserInformation u = dataSnapshot.getValue(_UserInformation.class);
                if (u.getAdminStatus()) setViewAdmin();
                else setViewUser();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void setViewAdmin() {
        setContentView(R.layout.activity_show_one_teachable_moment_admin);

        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextTitle.setEnabled(false);
        editTextRating = (EditText) findViewById(R.id.editTextRating);
        editTextRating.setEnabled(false);
        editTextTeachableMoment = (EditText) findViewById(R.id.editTextTeachableMoment);
        editTextTeachableMoment.setKeyListener(null);
        editTextUserNickname = (EditText) findViewById(R.id.editTextUserNickname);
        editTextUserNickname.setEnabled(false);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextDate.setEnabled(false);
        buttonConfirm = (Button) findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(this);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        buttonDelete.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        buttonDelete.setOnClickListener(this);

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
        editTextDate.setText(tm.getDate().toString());

        if (tm.isConfirmed()) buttonConfirm.setText("Unconfirm");
        else {
            buttonConfirm.setText("Confirm");
            buttonConfirm.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
        }

        databaseReference.child("Benutzer").child(tm.getUserID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                _UserInformation u = dataSnapshot.getValue(_UserInformation.class);
                editTextUserNickname.setText(u.getForename() + " " + u.getSurname() + " (" + u.getNickname() + ")");
                editTextUserNickname.setTextColor(Color.GRAY);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void setViewUser() {
        setContentView(R.layout.activity_show_one_teachable_moment);

        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextTitle.setEnabled(false);
        editTextRating = (EditText) findViewById(R.id.editTextRating);
        editTextRating.setEnabled(false);
        editTextTeachableMoment = (EditText) findViewById(R.id.editTextTeachableMoment);
        editTextTeachableMoment.setKeyListener(null);
        editTextUserNickname = (EditText) findViewById(R.id.editTextUserNickname);
        editTextUserNickname.setEnabled(false);
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
        editTextDate.setText(tm.getDate().toString());

        databaseReference.child("Benutzer").child(tm.getUserID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                _UserInformation u = dataSnapshot.getValue(_UserInformation.class);
                editTextUserNickname.setText(u.getNickname());
                editTextUserNickname.setTextColor(Color.GRAY);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        final DatabaseReference dbRating = databaseReference.child("UnconfirmedMoments").child(tm.getId()).child("ratings");
        if (tm.getUserID().equals(firebaseAuth.getCurrentUser().getUid())) ratingBar.setVisibility(View.INVISIBLE);


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

                int countRatings = (int) dataSnapshot.getChildrenCount();
                databaseReference.child("UnconfirmedMoments").child(tm.getId()).child("countRatings").setValue(countRatings);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == buttonConfirm) confirmTM();
        if (view == buttonDelete) deleteTM();
    }

    private void confirmTM() {
        databaseReference.child("UnconfirmedMoments").child(tm.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                _TeachableMomentInformation tm = dataSnapshot.getValue(_TeachableMomentInformation.class);
                if (tm.isConfirmed()) {
                    databaseReference.child("UnconfirmedMoments").child(tm.getId()).child("confirmed").setValue(false);
                    buttonConfirm.setText("Confirm");
                    buttonConfirm.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                    updateTmCounter();
                } else if (!tm.isConfirmed()){
                    databaseReference.child("UnconfirmedMoments").child(tm.getId()).child("confirmed").setValue(true);
                    buttonConfirm.setText("Unconfirmed");
                    buttonConfirm.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                    updateTmCounter();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

    private void updateTmCounter() {
        databaseReference.child("UnconfirmedMoments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int tmCount = 0;
                String userIDTM = tm.getUserID();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    _TeachableMomentInformation tmDB = postSnapshot.getValue(_TeachableMomentInformation.class);
                    if (tmDB.getUserID().equals(userIDTM) && tmDB.isConfirmed()){
                        tmCount++;
                    }
                }
                databaseReference.child("Benutzer").child(tm.getUserID()).child("tmCounter").setValue(tmCount);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void deleteTM() {
        new AlertDialog.Builder(this)
                .setTitle("Moment löschen")
                .setMessage("Möchten Sie diesen Moment wirklich löschen?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        databaseReference.child("UnconfirmedMoments").child(tm.getId()).removeValue();
                        updateTmCounter();
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}