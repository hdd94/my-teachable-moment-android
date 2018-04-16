package com.example.android.firebaseauthdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ContactSupportActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextSubject;
    private EditText editTextContactSupport;
    private Button btnSend;
    private Toolbar toolbar;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_support);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), ShowTitleScreen.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();


        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextContactSupport = (EditText) findViewById(R.id.editTextContactSupport);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Support kontaktieren");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onClick(View view) {
        if(view == btnSend) {
            sendContactSupport();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void sendContactSupport() {

        String subject = editTextSubject.getText().toString();
        String contactSupport = editTextContactSupport.getText().toString();

        if(TextUtils.isEmpty(subject)) {
            Toast.makeText(this, "Bitte gebe einen Betreff ein.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(contactSupport)) {
            Toast.makeText(this, "Bitte gebe dein Anliege  ein.", Toast.LENGTH_SHORT).show();
            return;
        }

        String userID = firebaseAuth.getCurrentUser().getUid(); //Mail vom User herausfinden

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"huyducdo@googlemail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT   , contactSupport);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

//        String timeStamp = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss").format(Calendar.getInstance().getTime());
    }
}