package com.example.android.firebaseauthdemo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ShowTitleScreen extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;

    private Button btnContinue;
    private TextView textViewLogin;
//    private ImageView imageViewTitle;
    private TextView textViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(!isOnline()) {
            Toast.makeText(getApplicationContext(), "Bitte schalten Sie das Internet an!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setContentView(R.layout.activity_show_title_screen);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null) {
            finish();
            //Using "getApplicationContext()" because we are in addOnCompleteListener-Method
            startActivity(new Intent(getApplicationContext(), ShowTeachableMomentsActivity.class));
        }

        btnContinue = (Button) findViewById(R.id.btnContinue);
        textViewLogin = (TextView) findViewById(R.id.textViewLogin);
//        imageViewTitle = (ImageView) findViewById(R.id.imageViewTitle);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);

        btnContinue.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);

        String myTeaM = "<font face='verdana' color='#C61A27'><b>MyTeaM</b><br></font>";
        String hyphen = "-";
        String myTea = "<font color='#C61A27'><b> My Tea</b></font>";
        String chable = "chable";
        String m = "<font color='#C61A27'><b> M</b></font>";
        String oment = "oment ";
        textViewTitle.setText(Html.fromHtml(myTeaM +hyphen + myTea + chable + m + oment + hyphen));

//        imageViewTitle.setImageResource(R.drawable.titel_titelbildschirm);


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

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
