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

public class ShowMenuUserDataActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonChangeMail;
    private Button buttonChangePassword;
    private TextView textViewCounter;
    private Toolbar toolbar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_menu_change_user_data);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, ShowTitleScreen.class));
        }

        buttonChangeMail = (Button) findViewById(R.id.buttonChangeMail);
        buttonChangePassword = (Button) findViewById(R.id.buttonChangePassword);

        textViewCounter = (TextView) findViewById(R.id.textViewCounter);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Benutzerdaten ändern");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        buttonChangeMail.setOnClickListener(this);
        buttonChangePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view == buttonChangeMail) {
            startActivity(new Intent(this, ChangeMailActivity.class));
        }

        if(view == buttonChangePassword) {
            startActivity(new Intent(this, ChangePasswordActivity.class));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        onBackPressed();
        return true;
    }
}
