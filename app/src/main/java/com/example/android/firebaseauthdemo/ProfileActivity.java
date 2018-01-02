package com.example.android.firebaseauthdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private TextView textViewUserEmail;
    private Button btnLogout;

    private EditText editTextNickname, editTextForename, editTextSurname;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        //User isn't logged in
        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextNickname = (EditText) findViewById(R.id.editTextNickname);
        editTextForename = (EditText) findViewById(R.id.editTextForename);
        editTextSurname = (EditText) findViewById(R.id.editTextSurname);
        btnSave = (Button) findViewById(R.id.buttonSave);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        textViewUserEmail.setText("Welcome " + user.getEmail());

        btnLogout = (Button) findViewById(R.id.buttonLogout);

        btnLogout.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    private void saveUserInformation() {
        String nickname = editTextNickname.getText().toString().trim();
        String forename = editTextForename.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();

        UserInformation userInformation = new UserInformation(nickname, forename, surname);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userInformation);

        Toast.makeText(this, "Information Saved...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {

        if (view == btnLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if (view == btnSave) {
            saveUserInformation();
        }
    }
}
