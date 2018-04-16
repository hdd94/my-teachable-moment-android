package com.example.android.firebaseauthdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CreateTeachableMomentActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextTitle;
    private EditText editTextTeachableMoment;
    private EditText editTextPlace;
    private EditText editTextDate;
    private Button btnSave;
    private Toolbar toolbar;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private UserInformation userInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_teachable_moment);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), ShowTitleScreen.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();


        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextTeachableMoment = (EditText) findViewById(R.id.editTextTeachableMoment);
        editTextPlace = (EditText) findViewById(R.id.editTextPlace);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);


        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Teachable Moment");
        setSupportActionBar(toolbar);
        //Zurück-Button oben links
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initUsername();
    }

    @Override
    public void onClick(View view) {
        if(view == btnSave) {
            saveTeachableMoment();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void saveTeachableMoment () {

        String id = databaseReference.push().getKey();
        String title = editTextTitle.getText().toString();
        String teachableMoment = editTextTeachableMoment.getText().toString();
        String place = editTextPlace.getText().toString();
        String date = editTextDate.getText().toString();
        // TODO: Datepicker einbauen

        if(TextUtils.isEmpty(title)) {
            Toast.makeText(this, "Bitte gebe einen Titel ein.", Toast.LENGTH_SHORT).show();
            //TODO: Maximale Titellänge definieren
            return;
        }

        if(TextUtils.isEmpty(teachableMoment)) {
            Toast.makeText(this, "Bitte gebe ein Teachable Moment ein.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(place)) {
            Toast.makeText(this, "Bitte gebe einen Ort ein.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(date)) {
            Toast.makeText(this, "Bitte gebe ein Datum ein.", Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO: !!!!!!!!!!!!!!!!!!!!!!! Ein Objekt herausfinden mit UserID, sodass in Firebase nach Object gesucht wird mit der passenden ID
        String userID = firebaseAuth.getCurrentUser().getUid();
        String creationDate = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss").format(Calendar.getInstance().getTime());
        TeachableMomentInformation teachableMomentInformation = new TeachableMomentInformation(id, title, teachableMoment, place, date, creationDate, userID, userInformation);
        databaseReference.child("UnconfirmedMoments").child(id).setValue(teachableMomentInformation).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                finish();
                //Using "getApplicationContext()" because we are in addOnCompleteListener-Method
//                startActivity(new Intent(getApplicationContext(), ShowTeachableMomentsActivity.class));
                Toast.makeText(getApplicationContext(), "Teachable Moment erfolgreich gespeichert.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUsername() {
        String userID = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child("Benutzer").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               userInformation = dataSnapshot.getValue(UserInformation.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}