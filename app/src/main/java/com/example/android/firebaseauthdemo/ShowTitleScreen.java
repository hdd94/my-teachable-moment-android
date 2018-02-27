package com.example.android.firebaseauthdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ShowTitleScreen extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;

    private Button btnContinue;
    private TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_title_screen);

        firebaseAuth = FirebaseAuth.getInstance();

        //If already an user logged in
        if(firebaseAuth.getCurrentUser() != null) {
            finish();
            //Using "getApplicationContext()" because we are in addOnCompleteListener-Method
            startActivity(new Intent(getApplicationContext(), ShowTeachableMomentsActivity.class));
        }

        btnContinue = (Button) findViewById(R.id.btnContinue);
        textViewLogin = (TextView) findViewById(R.id.textViewLogin);

        btnContinue.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnContinue) {
            startActivity(new Intent(this, RegisterUser1Activity.class)); //Start new Activity
        }

        if (view == textViewLogin) {
            startActivity(new Intent(this, LoginUserActivity.class)); //Start new Activity
        }
    }
}
