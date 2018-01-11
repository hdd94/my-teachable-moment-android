package com.example.android.firebaseauthdemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;
    private TextView textViewPasswordForget;
    private Toolbar toolbar;

    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        //If already an user logged in
        if(firebaseAuth.getCurrentUser() != null) {
            finish();
            //Using "getApplicationContext()" because we are in addOnCompleteListener-Method
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        btnSignIn = (Button) findViewById(R.id.buttonSignin);
        textViewSignup = (TextView) findViewById(R.id.textViewSignUp);
        textViewPasswordForget = (TextView) findViewById(R.id.textViewPasswordForget);

        toolbar = (Toolbar) findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnSignIn.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
        textViewPasswordForget.setOnClickListener(this);
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "gggggBitte gebe eine E-Mail ein.", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }

        if(TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "Bitte gebe ein Passwort ein.", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }

        //if validations are ok
        //we will first show a progressbar

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.INVISIBLE);

                        if (task.isSuccessful()) {
                            //start the profile activity

                            finish();
                            //Using "getApplicationContext()" because we are in addOnCompleteListener-Method
                            startActivity(new Intent(getApplicationContext(), TeachableMomentsListActivity.class));
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == btnSignIn) {
            userLogin();
        }

        if (view == textViewSignup) {
            finish(); //Finish this activity
            startActivity(new Intent(this, Register1Activity.class)); //Start new Activity
        }

        if (view == textViewPasswordForget) {
            finish();
            startActivity(new Intent(this, PasswordForgetActivity.class ));
        }
    }

    //TODO Prüfen, ob es funktioniert, dass der Zurückbutton auf die vorherige Activity zeigt
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
