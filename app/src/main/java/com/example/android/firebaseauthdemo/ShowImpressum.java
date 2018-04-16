package com.example.android.firebaseauthdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ShowImpressum extends AppCompatActivity{

    private TextView textViewTitle;
    private TextView textViewImpressum;
    private Toolbar toolbar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_impressum);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, ShowTitleScreen.class));
        }

        String myTeaM = "<font face='verdana' color='#C61A27'><b>MyTeaM</b><br></font>";
        String hyphen = "-";
        String myTea = "<font color='#C61A27'><b> My Tea</b></font>";
        String chable = "chable";
        String m = "<font color='#C61A27'><b> M</b></font>";
        String oment = "oment ";
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewTitle.setText(Html.fromHtml(myTeaM +hyphen + myTea + chable + m + oment + hyphen));

        textViewImpressum = (TextView) findViewById(R.id.textViewImpressum);
        textViewImpressum.setText(Html.fromHtml(getString(R.string.your_text)));

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Impressum");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
