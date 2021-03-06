package com.example.android.firebaseauthdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

public class RegisterUser2Activity extends AppCompatActivity implements View.OnClickListener{

    private Button btnRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordConfirmed;
    private TextView textViewSignin;
    private Toolbar toolbar;

    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user_2);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null) {
            finish();
            //Using "getApplicationContext()" because we are in addOnCompleteListener-Method
            startActivity(new Intent(getApplicationContext(), ShowTeachableMomentsActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);

        btnRegister = (Button) findViewById(R.id.buttonRegister);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        //TODO: E-Mail Verifikation hinzuf??gen
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        editTextPasswordConfirmed = (EditText) findViewById(R.id.editTextPasswordConfirmed);

        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Benutzer registrieren");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void registerUser() {

        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String passwordConfirmed = editTextPasswordConfirmed.getText().toString().trim();

        final String nickname = getIntent().getExtras().getString("Nickname");
        final String forename = getIntent().getExtras().getString("Vorname");
        final String surname = getIntent().getExtras().getString("Nachname");

        //TODO: Pr??fen, ob Nickname schon evtl. im System ist. Und dies dann sperren

        final String creationDate = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss").format(Calendar.getInstance().getTime());

        if(TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Bitte gebe eine E-Mail ein.", Toast.LENGTH_SHORT).show();
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

        if(!Character.isUpperCase(password.codePointAt(0))) {
            Toast.makeText(this, "Das Passwort muss mit einem Gro??buchstaben beginnen.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!(password.equals(passwordConfirmed))) {
            //password is empty
            Toast.makeText(this, "Bitte best??tige dein Passwort richtig.", Toast.LENGTH_SHORT).show();
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
                            _UserInformation userInformation = new _UserInformation(nickname, forename, surname, email, creationDate);
                            databaseReference.child("Benutzer").child(user.getUid()).setValue(userInformation).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(getApplicationContext(), ShowTeachableMomentsActivity.class));
                                    finishAffinity();
                                }
                            });
                        } else {
                            try {
                                throw task.getException();
                            }
                            catch (FirebaseAuthWeakPasswordException weakPassword)
                            {
                                Toast.makeText(RegisterUser2Activity.this, "Registrierung nicht erfolgreich... ??berpr??fe dein Passwort", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            // if user enters wrong password.
                            catch (FirebaseAuthInvalidCredentialsException malformedEmail)
                            {
                                Toast.makeText(RegisterUser2Activity.this, "Registrierung nicht erfolgreich... ??berpr??fe deine E-Mail", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            catch (FirebaseAuthUserCollisionException existEmail)
                            {
                                Toast.makeText(RegisterUser2Activity.this, "Registrierung nicht erfolgreich... E-Mail bereits vorhanden", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            catch (Exception e)
                            {
                                Toast.makeText(RegisterUser2Activity.this, "Registrierung nicht erfolgreich... Bitte kontaktiere den Support", Toast.LENGTH_SHORT).show();
                                task.getException().printStackTrace();
                                return;
                            }
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
            finish();
            startActivity(new Intent(this, LoginUserActivity.class));
        }
    }

    //TODO Pr??fen, ob es funktioniert, dass der Zur??ckbutton auf die vorherige Activity zeigt
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
