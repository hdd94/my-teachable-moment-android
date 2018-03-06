package com.example.android.firebaseauthdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail;
    private Button btnSend;

    private Toolbar toolbar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null) {
            finish();
            //Using "getApplicationContext()" because we are in addOnCompleteListener-Method
            startActivity(new Intent(getApplicationContext(), ShowTeachableMomentsActivity.class));
        }

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        btnSend = (Button) findViewById(R.id.btnSend);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Passwort vergessen");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnSend.setOnClickListener(this);
    }

    private void passwordForget() {
        String email = editTextEmail.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Bitte gebe eine E-Mail ein.", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "E-Mail wurde erfolgreich gesendet.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Problem mit der E-Mail wurde festgestellt!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == btnSend) {
            passwordForget();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
