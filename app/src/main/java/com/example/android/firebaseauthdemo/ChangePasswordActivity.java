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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextPassword;
    private EditText editTextPasswordValidate;
    private Button btnSend;

    private Toolbar toolbar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), ShowTitleScreen.class));
        }

        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPasswordValidate = (EditText) findViewById(R.id.editTextPasswordValidate);
        btnSend = (Button) findViewById(R.id.btnSend);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Passwort ändern");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnSend.setOnClickListener(this);
    }

    private void changePassword() {

        String password = editTextPassword.getText().toString().trim();
        String passwordValidate = editTextPasswordValidate.getText().toString().trim();

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Bitte gebe ein Passwort ein.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(passwordValidate)){
            Toast.makeText(this, "Bitte wiederhole dein Passwort.", Toast.LENGTH_SHORT).show();
            return;
        }


        if(password.equals(passwordValidate)) {
            firebaseAuth.getCurrentUser().updatePassword(password)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Passwort wurde erfolgreich geändert.", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), ShowMenuActivity.class));
                            } else {
                                System.out.println(task.getResult());
                                Toast.makeText(getApplicationContext(), "Problem mit dem Passwort wurde festgestellt!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else {
            Toast.makeText(this, "Bitte überprüfe dein Passwort.", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnSend) {
            changePassword();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
