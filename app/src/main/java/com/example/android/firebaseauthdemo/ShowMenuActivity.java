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

public class ShowMenuActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonChangeUserData;
    private Button buttonContactSupport;
    private Button buttonShowImpressum;
    private Button buttonLogOut;
    private TextView textViewLoggedInAs;
    private Toolbar toolbar;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_menu);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, ShowTitleScreen.class));
        }

        buttonChangeUserData = (Button) findViewById(R.id.buttonChangeUserData);
        buttonContactSupport = (Button) findViewById(R.id.buttonContactSupport);
        buttonShowImpressum = (Button) findViewById(R.id.buttonShowImpressum);
        buttonLogOut = (Button) findViewById(R.id.buttonLogOut);

        textViewLoggedInAs = (TextView) findViewById(R.id.textViewLoggedInAs);
        setLoggedInAs();

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Einstellungen");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        buttonChangeUserData.setOnClickListener(this);
        buttonContactSupport.setOnClickListener(this);
        buttonShowImpressum.setOnClickListener(this);
        buttonLogOut.setOnClickListener(this);
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

        if(view == buttonChangeUserData) {
            startActivity(new Intent(this, ReAuthenticateUserActivity.class));
        }

        if(view == buttonContactSupport) {
            startActivity(new Intent(this, ContactSupportActivity.class));
        }

        if(view == buttonShowImpressum) {
            startActivity(new Intent(this, ShowImpressum.class));
        }

        if(view == buttonLogOut) {
            Intent i = new Intent(this, ShowTitleScreen.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            firebaseAuth.signOut();

//            Intent intent = new Intent(this, ShowTitleScreen.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            finish();

//            Intent intent = new Intent(this, ShowTitleScreen.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(new Intent(this, ShowTitleScreen.class));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
