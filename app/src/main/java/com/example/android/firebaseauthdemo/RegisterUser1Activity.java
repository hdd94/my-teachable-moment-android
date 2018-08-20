package com.example.android.firebaseauthdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterUser1Activity extends AppCompatActivity implements View.OnClickListener{

    private Button btnContinue;
    private EditText editTextNickname;
    private EditText editTextForename;
    private EditText editTextSurname;
    private TextView textViewSignin;
    private Toolbar toolbar;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    private boolean nicknameExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user_1);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null) {
            finish();
            //Using "getApplicationContext()" because we are in addOnCompleteListener-Method
            startActivity(new Intent(getApplicationContext(), ShowTeachableMomentsActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        btnContinue = (Button) findViewById(R.id.buttonContinue);

        editTextNickname = (EditText) findViewById(R.id.editTextNickname);
        editTextForename = (EditText) findViewById(R.id.editTextForename);
        editTextSurname = (EditText) findViewById(R.id.editTextSurname);

        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Benutzer registrieren");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnContinue.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }


    //TODO: Zurück-Button oben links noch reinimplementieren
    private void continueRegistration() {
        String nickname = editTextNickname.getText().toString().trim();
        String forename = editTextForename.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();

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

        if(TextUtils.isEmpty(nickname)) {
            //email is empty
            Toast.makeText(this, "Bitte gebe einen Nicknamen ein.", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }

        if(!Character.isUpperCase(nickname.codePointAt(0))) {
            Toast.makeText(this, "Der Nickname muss mit einem Großbuchstaben beginnen.", Toast.LENGTH_SHORT).show();
            return;
        }

//        checkIfNicknameExists(nickname);
        if(nicknameExists) {
            Toast.makeText(this, "Der Nickname ist schon vorhanden.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, RegisterUser2Activity.class);
        intent.putExtra("Nickname", nickname);
        intent.putExtra("Vorname", forename);
        intent.putExtra("Nachname", surname);
        startActivity(intent);
    }

//    public void checkIfNicknameExists(final String nickname) {
//        databaseReference.child("Benutzer").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    _UserInformation u = postSnapshot.getValue(_UserInformation.class);
//                    if (u.getNickname().equals(nickname)) nicknameExists = true;
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//        });
//    }

    @Override
    public void onClick(View view) {
        if(view == btnContinue) {
            continueRegistration();
        }

        if(view == textViewSignin) {
            finish();
            startActivity(new Intent(this, LoginUserActivity.class));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
