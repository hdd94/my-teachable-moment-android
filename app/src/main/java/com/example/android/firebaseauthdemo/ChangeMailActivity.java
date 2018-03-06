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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ChangeMailActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail;
    private EditText editTextEmailValidate;
    private Button btnSend;

    private Toolbar toolbar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_mail);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), ShowTitleScreen.class));
        }

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextEmailValidate = (EditText) findViewById(R.id.editTextEmailValidate);
        btnSend = (Button) findViewById(R.id.btnSend);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("E-Mail ändern");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnSend.setOnClickListener(this);
    }

    private void changeMail() {

        String mail = editTextEmail.getText().toString().trim();
        String mailValidate = editTextEmailValidate.getText().toString().trim();

        if(TextUtils.isEmpty(mail)){
            Toast.makeText(this, "Bitte gebe eine E-Mail ein.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(mailValidate)){
            Toast.makeText(this, "Bitte wiederhole deine E-Mail.", Toast.LENGTH_SHORT).show();
            return;
        }


        if(mail.equals(mailValidate)) {
            firebaseAuth.getCurrentUser().updateEmail(mail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "E-Mail wurde erfolgreich geändert.", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), ShowMenuActivity.class));
                            } else {
                                System.out.println(task.getResult());
                                Toast.makeText(getApplicationContext(), "Problem mit der E-Mail wurde festgestellt!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else {
            Toast.makeText(this, "Bitte überprüfe deine E-Mail.", Toast.LENGTH_SHORT).show();
            return;
        }
        //TODO: Mail verifizieren einbauen, auch bei Registrierung
    }

    @Override
    public void onClick(View view) {
        if (view == btnSend) {
            changeMail();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
