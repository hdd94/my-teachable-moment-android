package com.example.android.firebaseauthdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class ReAuthenticateUserActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnAuthenticate;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Toolbar toolbar;

    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_authenticate_user);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), ShowTitleScreen.class));
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        btnAuthenticate = (Button) findViewById(R.id.buttonAuthenticate);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Benutzer authentifizieren");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnAuthenticate.setOnClickListener(this);
    }

    private void userAuthenticate() {

        //TODO: Authentication einbauen, um vor dem Einstellungsmenü noch Benutzer zu authentifizieren und
        //TODO: Mit den Credentials alles zu authentifizieren

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Bitte gebe deine E-Mail ein.", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }

        if(TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "Bitte gebe dein Passwort ein.", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, password);

        // Prompt the user to re-provide their sign-in credentials
        firebaseAuth.getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.INVISIBLE);

                        if (task.isSuccessful()) {
                            //start the profile activity

                            finish();
                            //Using "getApplicationContext()" because we are in addOnCompleteListener-Method
                            startActivity(new Intent(getApplicationContext(), ShowMenuChangeUserDataActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Bitte überprüfen Sie Ihre Anmeldedaten", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//        firebaseAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        progressBar.setVisibility(View.INVISIBLE);
//
//                        if (task.isSuccessful()) {
//                            //start the profile activity
//
//                            finish();
//                            //Using "getApplicationContext()" because we are in addOnCompleteListener-Method
//                            startActivity(new Intent(getApplicationContext(), ShowTeachableMomentsActivity.class));
//                        }
//                    }
//                });
    }

    @Override
    public void onClick(View view) {
        if (view == btnAuthenticate) {
            userAuthenticate();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
