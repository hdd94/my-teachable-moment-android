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

import com.google.firebase.auth.FirebaseAuth;

public class TeachableMomentActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextTitle;
    private EditText editTextTeachableMoment;
    private EditText editTextPlace;
    private EditText editTextDate;
    private Button btnSave;
    private Toolbar toolbar;

    private FirebaseAuth firebaseAuth;

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
}
