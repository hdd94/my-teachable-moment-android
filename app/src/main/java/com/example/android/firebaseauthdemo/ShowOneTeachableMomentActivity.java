package com.example.android.firebaseauthdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ShowOneTeachableMomentActivity extends AppCompatActivity{

    private EditText editTextTitle;
    private EditText editTextTeachableMoment;
    private EditText editTextPlace;
    private EditText editTextDate;
    private RatingBar ratingBar;
    private Toolbar toolbar;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private TeachableMomentInformation tm;

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
        editTextTeachableMoment = (EditText) findViewById(R.id.editTextTeachableMoment);
//        editTextTeachableMoment.setEnabled(false);
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

        tm = (TeachableMomentInformation) getIntent().getParcelableExtra("TeachableMoment");
        editTextTitle.setText(tm.getTitle());
        editTextTeachableMoment.setText(tm.getTeachableMoment());
        editTextPlace.setText(tm.getPlace());
        editTextDate.setText(tm.getDate());

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(getApplicationContext(), "Stars: " + (int)rating, Toast.LENGTH_SHORT).show();
                databaseReference.child("UnconfirmedMoments").child(tm.getId()).child("ratings");
                Toast.makeText(getApplicationContext(), "Beitrag wurde erfolgreich bewertet.", Toast.LENGTH_SHORT).show();
                //TODO: Bewertung speichern --> Durchschnittsbewertung berechnen
//                databaseReference.child("Benutzer").child(titel.getUid()).setValue(userInformation);
                //TODO: Bewertung als Extra-Objekt wie TeachableMoments etc. speichern.
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

//    private void saveTeachableMoment () {
//
//        String id = databaseReference.push().getKey();
//        String nickname = editTextTitle.getText().toString();
//        String teachableMoment = editTextTeachableMoment.getText().toString();
//        String place = editTextPlace.getText().toString();
//        String date = editTextDate.getText().toString();
//        // TODO: Datepicker einbauen
//
//        if(TextUtils.isEmpty(nickname)) {
//            Toast.makeText(this, "Bitte gebe einen Titel ein.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if(TextUtils.isEmpty(teachableMoment)) {
//            Toast.makeText(this, "Bitte gebe ein Teachable Moment ein.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if(TextUtils.isEmpty(place)) {
//            Toast.makeText(this, "Bitte gebe einen Ort ein.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if(TextUtils.isEmpty(date)) {
//            Toast.makeText(this, "Bitte gebe ein Datum ein.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String userID = firebaseAuth.getCurrentUser().getUid();
//        String timeStamp = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss").format(Calendar.getInstance().getTime());
//        TeachableMomentInformation teachableMomentInformation = new TeachableMomentInformation(id, nickname, teachableMoment, place, date, userID, timeStamp);
//        databaseReference.child("UnconfirmedMoments").child(id).setValue(teachableMomentInformation).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                finish();
//                //Using "getApplicationContext()" because we are in addOnCompleteListener-Method
//                startActivity(new Intent(getApplicationContext(), ShowTeachableMomentsActivity.class));
//            }
//        });
//        Toast.makeText(this, "Teachable Moment erfolgreich gespeichert.", Toast.LENGTH_SHORT).show();
//    }
}