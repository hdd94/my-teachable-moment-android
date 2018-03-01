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

public class ShowMenuActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonChangeMail;
    private Button buttonChangePassword;
    private Button buttonExtendCounter;
    private Button buttonContactSupport;
    private Button buttonShowImpressum;
    private Button buttonLogOut;
    private TextView textViewCounter;
    private Toolbar toolbar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_menu);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, ShowTitleScreen.class));
        }

        buttonChangeMail = (Button) findViewById(R.id.buttonChangeMail);
        buttonChangePassword = (Button) findViewById(R.id.buttonChangePassword);
        buttonExtendCounter = (Button) findViewById(R.id.buttonExtendCounter);
        buttonContactSupport = (Button) findViewById(R.id.buttonContactSupport);
        buttonShowImpressum = (Button) findViewById(R.id.buttonShowImpressum);
        buttonLogOut = (Button) findViewById(R.id.buttonLogOut);

        textViewCounter = (TextView) findViewById(R.id.textViewCounter);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Einstellungen");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        buttonChangeMail.setOnClickListener(this);
        buttonChangePassword.setOnClickListener(this);
        buttonExtendCounter.setOnClickListener(this);
        buttonContactSupport.setOnClickListener(this);
        buttonShowImpressum.setOnClickListener(this);
        buttonLogOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view == buttonChangeMail) {
            Toast.makeText(getApplicationContext(), "Ändere Mail...", Toast.LENGTH_SHORT).show();
        }

        if(view == buttonChangePassword) {
            Toast.makeText(getApplicationContext(), "Ändere Passwort...", Toast.LENGTH_SHORT).show();
        }

        if(view == buttonExtendCounter) {
            Toast.makeText(getApplicationContext(), "Verlängere App-Counter...", Toast.LENGTH_SHORT).show();
        }

        if(view == buttonContactSupport) {
            Toast.makeText(getApplicationContext(), "Kontaktiere Support...", Toast.LENGTH_SHORT).show();
        }

        if(view == buttonShowImpressum) {
            startActivity(new Intent(this, ShowImpressum.class));
        }

        if(view == buttonLogOut) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, ShowTitleScreen.class));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
