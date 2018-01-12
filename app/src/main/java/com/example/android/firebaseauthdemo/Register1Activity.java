package com.example.android.firebaseauthdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Register1Activity extends AppCompatActivity implements View.OnClickListener{

    private Button btnContinue;
    private EditText editTextNickname;
    private EditText editTextForename;
    private EditText editTextSurname;
    private TextView textViewSignin;
    private Toolbar toolbar;

    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        firebaseAuth = FirebaseAuth.getInstance();

        //If already an user logged in
        if(firebaseAuth.getCurrentUser() != null) {
            finish();
            //Using "getApplicationContext()" because we are in addOnCompleteListener-Method
            startActivity(new Intent(getApplicationContext(), TeachableMomentsListActivity.class));
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);

        btnContinue = (Button) findViewById(R.id.buttonContinue);

        editTextNickname = (EditText) findViewById(R.id.editTextNickname);
        editTextForename = (EditText) findViewById(R.id.editTextForename);
        editTextSurname = (EditText) findViewById(R.id.editTextSurname);

        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        toolbar = (Toolbar) findViewById(R.id.toolbar_register1);
        setSupportActionBar(toolbar);

        btnContinue.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void continueRegistration() {
        String nickname = editTextNickname.getText().toString().trim();
        String forename = editTextForename.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();

        if(TextUtils.isEmpty(nickname)) {
            //email is empty
            Toast.makeText(this, "Bitte gebe einen Nicknamen ein.", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }

        if(TextUtils.isEmpty(forename)) {
            //password is empty
            Toast.makeText(this, "Bitte gebe einen Vornamen ein.", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }

        if(TextUtils.isEmpty(surname)) {
            //password is empty
            Toast.makeText(this, "Bitte geben einen Nachnamen ein.", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }

        Intent intent = new Intent(this, Register2Activity.class);
        intent.putExtra("Nickname", nickname);
        intent.putExtra("Vorname", forename);
        intent.putExtra("Nachname", surname);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(view == btnContinue) {
            continueRegistration();
        }

        if(view == textViewSignin) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
