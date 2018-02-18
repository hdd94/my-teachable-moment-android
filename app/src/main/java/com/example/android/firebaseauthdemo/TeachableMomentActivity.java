package com.example.android.firebaseauthdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TeachableMomentActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextTitle;
    private EditText editTextTeachableMoment;
    private EditText editTextPlace;
    private EditText editTextDate;
    private Button btnSave;
    private Toolbar toolbar;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachable_moment);

        firebaseAuth = FirebaseAuth.getInstance();

        //If already an user logged in
        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();


        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextTeachableMoment = (EditText) findViewById(R.id.editTextTeachableMoment);
        editTextPlace = (EditText) findViewById(R.id.editTextPlace);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);


        toolbar = (Toolbar) findViewById(R.id.toolbar_teachable_moment);
        setSupportActionBar(toolbar);
        //Zurück-Button oben links
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onClick(View view) {
        if(view == btnSave) {
            //TODO Daten speichern
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void saveTeachableMoment () {

        String title = editTextTitle.getText().toString();
        String teachableMoment = editTextTeachableMoment.getText().toString();
        String place = editTextPlace.getText().toString();
        String date = editTextDate.getText().toString();
        // TODO: Datepicker einbauen

        if(TextUtils.isEmpty(title)) {
            Toast.makeText(this, "Bitte gebe einen Titel ein.", Toast.LENGTH_SHORT).show();
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

        FirebaseUser user = firebaseAuth.getCurrentUser();
        Date creationDate = new Date();
//        SimpleDateFormat ft = new SimpleDateFormat("E dd.MM.yyyy");
//        creationDate = ft.format(creationDate);
        TeachableMomentInformation teachableMomentInformation = new TeachableMomentInformation(title, teachableMoment, place, date, user, creationDate);
        databaseReference.child("UnconfirmedMoments").child(user.getUid()).setValue(userInformation).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                finish();
                //Using "getApplicationContext()" because we are in addOnCompleteListener-Method
                startActivity(new Intent(getApplicationContext(), TeachableMomentsListActivity.class));
    }
}
