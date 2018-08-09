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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowMenuChangeUserDataActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonChangeMail;
    private Button buttonChangePassword;
    private TextView textViewLoggedInAs;
    private Toolbar toolbar;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_menu_change_user_data);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, ShowTitleScreen.class));
        }

        buttonChangeMail = (Button) findViewById(R.id.buttonChangeMail);
        buttonChangePassword = (Button) findViewById(R.id.buttonChangePassword);

        textViewLoggedInAs = (TextView) findViewById(R.id.textViewLoggedInAs);
        setLoggedInAs();

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Benutzerdaten ändern");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        buttonChangeMail.setOnClickListener(this);
        buttonChangePassword.setOnClickListener(this);
    }

    private void setLoggedInAs() {
        String userID = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child("Benutzer").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                _UserInformation u = dataSnapshot.getValue(_UserInformation.class);
                textViewLoggedInAs.setText("Eingeloggt als: " + u.getNickname());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
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
