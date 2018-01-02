package com.example.android.firebaseauthdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register2Activity extends AppCompatActivity implements View.OnClickListener{

    private Button btnRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordConfirmed;
    private TextView textViewSignin;

    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
//    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        firebaseAuth = FirebaseAuth.getInstance();

        //If already an user logged in
        if(firebaseAuth.getCurrentUser() != null) {
            finish();
            //Using "getApplicationContext()" because we are in addOnCompleteListener-Method
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
//        databaseReference = FirebaseFirestore.getInstance();

        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);

        btnRegister = (Button) findViewById(R.id.buttonRegister);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPasswordConfirmed = (EditText) findViewById(R.id.editTextPasswordConfirmed);

        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        btnRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void registerUser() {


        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String passwordConfirmed = editTextPasswordConfirmed.getText().toString().trim();

        final String nickname = getIntent().getExtras().getString("Nickname");
        final String forename = getIntent().getExtras().getString("Vorname");
        final String surname = getIntent().getExtras().getString("Nachname");

        if(TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Bitte gebe eine E-Mail ein.", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }

        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Bitte gebe ein Passwort ein.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length() < 6) {
            Toast.makeText(this, "Das Passwort muss mind. 6 Zeichen enthalten.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!(password.equals(passwordConfirmed))) {
            //password is empty
            Toast.makeText(this, "Bitte bestätige dein Passwort richtig.", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }

        //if validations are ok
        //we will first show a progressbar

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.createUserWithEmailAndPassword(email, password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.INVISIBLE);

                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            UserInformation userInformation = new UserInformation(nickname, forename, surname);
                            databaseReference.child("Benutzer").child(user.getUid()).setValue(userInformation).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    finish();
                                    //Using "getApplicationContext()" because we are in addOnCompleteListener-Method
                                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                                }
                            });
                        } else {
                            Toast.makeText(Register2Activity.this, "Could not register... please try again", Toast.LENGTH_SHORT).show();
                            //Gibt raus was für ein Problem der Task hat
                            task.getException().printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == btnRegister) {
            registerUser();
        }

        if(view == textViewSignin) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
