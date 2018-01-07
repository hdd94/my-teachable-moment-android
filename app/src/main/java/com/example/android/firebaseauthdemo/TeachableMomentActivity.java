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

    private Button btnContinue;
    private EditText editTextNickname;
    private EditText editTextForename;
    private EditText editTextSurname;
    private TextView textViewSignin;
    private Toolbar toolbar;

    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachable_moment);

        firebaseAuth = FirebaseAuth.getInstance();

        //If already an user logged in
        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            //Using "getApplicationContext()" because we are in addOnCompleteListener-Method
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

//        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);
//
//        btnContinue = (Button) findViewById(R.id.buttonContinue);
//
//        editTextNickname = (EditText) findViewById(R.id.editTextNickname);
//        editTextForename = (EditText) findViewById(R.id.editTextForename);
//        editTextSurname = (EditText) findViewById(R.id.editTextSurname);
//
//        textViewSignin = (TextView) findViewById(R.id.textViewSignin);
//
//        toolbar = (Toolbar) findViewById(R.id.toolbar_teachable_moment);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//
//        btnContinue.setOnClickListener(this);
//        textViewSignin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
    }

    //TODO Prüfen, ob es funktioniert, dass der Zurückbutton auf die vorherige Activity zeigt
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
