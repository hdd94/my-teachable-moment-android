package com.example.android.firebaseauthdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ShowMenuActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonChangeUserData;
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

        buttonChangeUserData = (Button) findViewById(R.id.buttonChangeUserData);
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

        buttonChangeUserData.setOnClickListener(this);
        buttonExtendCounter.setOnClickListener(this);
        buttonContactSupport.setOnClickListener(this);
        buttonShowImpressum.setOnClickListener(this);
        buttonLogOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view == buttonChangeUserData) {
            startActivity(new Intent(this, ReAuthenticateUserActivity.class));
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
